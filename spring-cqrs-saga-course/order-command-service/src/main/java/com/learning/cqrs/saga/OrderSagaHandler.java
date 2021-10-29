package com.learning.cqrs.saga;

import java.time.Duration;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.cqrs.commands.FailureOrderCommand;
import com.learning.cqrs.commands.ReserveProductCommand;
import com.learning.cqrs.commands.SuccessOrderCommand;
import com.learning.cqrs.commands.UndoReserveProductCommand;
import com.learning.cqrs.commands.UserBalanceCreditCommand;
import com.learning.cqrs.commands.UserBalanceDebitCommand;
import com.learning.cqrs.events.CreateOrderEvent;
import com.learning.cqrs.events.FailureOrderEvent;
import com.learning.cqrs.events.ReserveProductEvent;
import com.learning.cqrs.events.SuccessOrderEvent;
import com.learning.cqrs.events.UndoReserveProductEvent;
import com.learning.cqrs.events.UserBalanceCreditEvent;
import com.learning.cqrs.events.UserBalanceDebitEvent;
import com.learning.cqrs.queries.FindUserPreferenceByID;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class OrderSagaHandler {

	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	private transient QueryGateway queryGateway;
	
	@Autowired
	private transient DeadlineManager deadlineManager;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(CreateOrderEvent createOrderEvent) {
		log.info("handle.createOrderEvent called with data: "+createOrderEvent);
		
		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder().productId(createOrderEvent.getProductId())
						.quantity(createOrderEvent.getQuantity())
						.userId(createOrderEvent.getUserId())
						.orderId(createOrderEvent.getOrderId())
						.build();
		
		
		commandGateway.send(reserveProductCommand, (commandMessage , commandResultMessage) -> {
			if(commandResultMessage.isExceptional()) {
				//fall back undo command events
				//like remove the created order or update the status to failed
				//again need to be updated in both command and query for order and product apis
				
				FailureOrderCommand failureOrderCommand = FailureOrderCommand.builder()
						.orderId(createOrderEvent.getOrderId()).userId(createOrderEvent.getUserId())
						.productId(createOrderEvent.getProductId()).quantity(createOrderEvent.getQuantity())
						.build();

				commandGateway.send(failureOrderCommand);
			}
		});
		
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ReserveProductEvent reserveProductEvent) {
		log.info("handle.reserveProductEvent called with data: "+reserveProductEvent);
			double defaultPrice = 10d;
		  UserBalanceDebitCommand userBalanceDebitCommand = UserBalanceDebitCommand.builder()
		  		.userId(reserveProductEvent.getUserId())
		  		.balance(reserveProductEvent.getQuantity() * defaultPrice)
		  		.orderId(reserveProductEvent.getOrderId())
		  		.productId(reserveProductEvent.getProductId())
		  		.quantity(reserveProductEvent.getQuantity())
		  		.build()
		  		;
		  
		  //assume billing api is time consuming as it is overloaded and hence user debit should happen within time line
		  //for this specific command i expect the succes event to be called within the duration of 1 seconds
		  deadlineManager.schedule(Duration.ofSeconds(10),ReserveProductEvent.DEADLINE_NAME , reserveProductEvent );
		  
		  //we will assume below code is not executed to demo deadline
		 // boolean deadlineExceeded = true;
		  
		  boolean deadlineExceeded = false;
		  if(deadlineExceeded) {
			  return;
		  }
		  
		  commandGateway.send(userBalanceDebitCommand, (commandMessage , commandResultMessage) -> {
				if(commandResultMessage.isExceptional()) {
					//fall back undo command events
					//upstock the product quantity and make order rejected
					//again need to be updated in both command and query for order and product apis
					UndoReserveProductCommand undoReserveProductCommand = new UndoReserveProductCommand();
					BeanUtils.copyProperties(reserveProductEvent, undoReserveProductCommand);
					
					commandGateway.send(undoReserveProductCommand);
					
					
				}
			});
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(UndoReserveProductEvent undoReserveProductEvent) {
		log.info("handle.undoReserveProductEvent called with data: " + undoReserveProductEvent);
		FailureOrderCommand failureOrderCommand = FailureOrderCommand.builder()
				.orderId(undoReserveProductEvent.getOrderId()).userId(undoReserveProductEvent.getUserId())
				.productId(undoReserveProductEvent.getProductId()).quantity(undoReserveProductEvent.getQuantity())
				.build();

		commandGateway.send(failureOrderCommand);

	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(UserBalanceCreditEvent userBalanceCreditEvent) {
		log.info("handle.userBalanceCreditEvent called with data: " + userBalanceCreditEvent);

		UndoReserveProductCommand undoReserveProductCommand = new UndoReserveProductCommand();
		BeanUtils.copyProperties(userBalanceCreditEvent, undoReserveProductCommand);
		
		commandGateway.send(undoReserveProductCommand);
		
	}

	//example of query among multi service using axon server
	//this will be end of saga , we updae the statue to success and update the user preference for shipment
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(UserBalanceDebitEvent userBalanceDebitEvent) {
		log.info("handle.userBalanceDebitEvent called with data: "+userBalanceDebitEvent);
		
		deadlineManager.cancelAll(ReserveProductEvent.DEADLINE_NAME );
		
		FindUserPreferenceByID findUserPreferenceByID = new FindUserPreferenceByID(userBalanceDebitEvent.getUserId());
		
		String preference = queryGateway.query(findUserPreferenceByID, String.class).join();
		log.info("handle.userBalanceDebitEvent preference recieved: "+preference);
		
		SuccessOrderCommand successOrderCommand = SuccessOrderCommand.builder()
								.orderId(userBalanceDebitEvent.getOrderId())
								.userId(userBalanceDebitEvent.getUserId())
								.balance(userBalanceDebitEvent.getBalance())
								.productId(userBalanceDebitEvent.getProductId())
								.quantity(userBalanceDebitEvent.getQuantity())
								.shipmentPreference(preference)
								.build();
		
		
		commandGateway.send(successOrderCommand, (commandMessage , commandResultMessage) -> {
			if(commandResultMessage.isExceptional()) {
				//fall back undo command events
				//credit back the money to user, upstock te product and then reject the order state
				
				
				  UserBalanceCreditCommand userBalanceCreditCommand = UserBalanceCreditCommand.builder()
					  		.userId(userBalanceDebitEvent.getUserId())
					  		.balance(userBalanceDebitEvent.getQuantity() * 10)
					  		.orderId(userBalanceDebitEvent.getOrderId())
					  		.productId(userBalanceDebitEvent.getProductId())
					  		.quantity(userBalanceDebitEvent.getQuantity())
					  		.build()
					  		;
				  
				  commandGateway.send(userBalanceCreditCommand);
				
			}
		});
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	public void handle(SuccessOrderEvent successOrderEvent) {
		log.info("handle.successOrderEvent succesfully placed order: "+successOrderEvent);
		
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	public void handle(FailureOrderEvent failureOrderEvent) {
		log.info("handle.failureOrderEvent error occurred while placing order: "+failureOrderEvent);
		
	}
	
	@DeadlineHandler(deadlineName = ReserveProductEvent.DEADLINE_NAME)
	public void deadline(ReserveProductEvent reserveProductEvent) {
			log.info("deadline reserveProductEvent called with data: "+reserveProductEvent);
			
			//so far product stock is closed so we need to updstock that
			UndoReserveProductCommand undoReserveProductCommand = new UndoReserveProductCommand();
			BeanUtils.copyProperties(reserveProductEvent, undoReserveProductCommand);
			
			commandGateway.send(undoReserveProductCommand);
	}
	
}
