package com.vp.plugin.sample.bpdplugin.actions;

import java.awt.Point;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IBusinessProcessDiagramUIModel;
import com.vp.plugin.diagram.IDiagramTypeConstants;
import com.vp.plugin.diagram.shape.IBPEndEventUIModel;
import com.vp.plugin.diagram.shape.IBPLaneUIModel;
import com.vp.plugin.diagram.shape.IBPPoolUIModel;
import com.vp.plugin.diagram.shape.IBPStartEventUIModel;
import com.vp.plugin.diagram.shape.IBPSubProcessUIModel;
import com.vp.plugin.diagram.shape.IBPTaskUIModel;
import com.vp.plugin.model.IBPEndEvent;
import com.vp.plugin.model.IBPLane;
import com.vp.plugin.model.IBPMessageFlow;
import com.vp.plugin.model.IBPPool;
import com.vp.plugin.model.IBPSequenceFlow;
import com.vp.plugin.model.IBPStartEvent;
import com.vp.plugin.model.IBPSubProcess;
import com.vp.plugin.model.IBPTask;
import com.vp.plugin.model.factory.IModelElementFactory;

public class BusinessProcessDiagramActionControl implements VPActionController {

	@Override
	public void performAction(VPAction arg0) {
		// Create blank Business Process Diagram
		DiagramManager diagramManager = ApplicationManager.instance().getDiagramManager();
		IBusinessProcessDiagramUIModel bpd = (IBusinessProcessDiagramUIModel) diagramManager.createDiagram(IDiagramTypeConstants.DIAGRAM_TYPE_BUSINESS_PROCESS_DIAGRAM);
		bpd.setName("Simple BPD");
		
		// Create the pool model for Financial Institution 
		IBPPool poolFinancialInstitution = IModelElementFactory.instance().createBPPool();
		poolFinancialInstitution.setName("Financial Institution");
		// Create the pool shape on diagram
		IBPPoolUIModel poolShapeFinancialInstitution = (IBPPoolUIModel) diagramManager.createDiagramElement(bpd, poolFinancialInstitution);
		poolShapeFinancialInstitution.setBounds(20, 50, 700, 120);
		// Turn off the auto stretch off pool option
		poolShapeFinancialInstitution.setAutoStretch(IBPPoolUIModel.AUTO_STRETCH_OFF);
		// Call to re-calculate caption position when render the diagram
		poolShapeFinancialInstitution.resetCaption();
		
		// Create pool model for Supplier
		IBPPool poolSupplier = IModelElementFactory.instance().createBPPool();
		poolSupplier.setName("Supplier");
		// Create the pool shape on diagram
		IBPPoolUIModel poolShapeSupplier = (IBPPoolUIModel) diagramManager.createDiagramElement(bpd, poolSupplier);
		poolShapeSupplier.setBounds(20, 210, 700, 240);
		// Turn off the auto stretch off pool option		
		poolShapeSupplier.setAutoStretch(IBPPoolUIModel.AUTO_STRETCH_OFF);
		// Call to re-calculate caption position when render the diagram
		poolShapeSupplier.resetCaption();
		
		// Create the Distribution lane under Supplier pool
		IBPLane laneDistribution = poolSupplier.createBPLane();
		laneDistribution.setName("Distribution");		
		// Create the lane shape for Distribution
		IBPLaneUIModel laneShapeDistribution = (IBPLaneUIModel) diagramManager.createDiagramElement(bpd, laneDistribution);
		poolShapeSupplier.addChild(laneShapeDistribution);
		laneShapeDistribution.setBounds(35, 210, 685, 120);
		laneShapeDistribution.resetCaption();
		
		// Create the Sales lane model and shape under Supplier pool
		IBPLane laneSales = poolSupplier.createBPLane();
		laneSales.setName("Sales");		
		IBPLaneUIModel laneShapeSales = (IBPLaneUIModel) diagramManager.createDiagramElement(bpd, laneSales);
		poolShapeSupplier.addChild(laneShapeSales);
		laneShapeSales.setBounds(35, 330, 685, 120);
		laneShapeSales.resetCaption();
		
		// Create start event model and shape under Sales lane
		IBPStartEvent startSales = laneSales.createBPStartEvent();
		IBPStartEventUIModel startShapeSales = (IBPStartEventUIModel) diagramManager.createDiagramElement(bpd, startSales);
		laneShapeSales.addChild(startShapeSales);
		startShapeSales.setBounds(70, 380, 20, 20);
		
		// Create Authorize Payment task model and shape under Sales lane
		IBPTask taskAuthorizePayment = laneSales.createBPTask();
		taskAuthorizePayment.setName("Authorize Payment");
		IBPTaskUIModel taskShapeAuthorizePayment = (IBPTaskUIModel) diagramManager.createDiagramElement(bpd, taskAuthorizePayment);
		laneShapeSales.addChild(taskShapeAuthorizePayment);
		taskShapeAuthorizePayment.setBounds(130, 370, 80, 40);
		taskShapeAuthorizePayment.resetCaption();
		
		// Create Process Order sub-process model and shape under Sales lane
		IBPSubProcess subprocessProcessOrder = laneSales.createBPSubProcess();
		subprocessProcessOrder.setName("Process Order");
		IBPSubProcessUIModel subprocessShapeProcessOrder = (IBPSubProcessUIModel) diagramManager.createDiagramElement(bpd, subprocessProcessOrder);
		laneShapeSales.addChild(subprocessShapeProcessOrder);
		subprocessShapeProcessOrder.setBounds(260, 370, 90, 40);
		subprocessShapeProcessOrder.resetCaption();
		
		// Create Pack Goods task model and shape under Distribution lane
		IBPTask taskPackGoods = laneDistribution.createBPTask();
		taskPackGoods.setName("Pack Goods");
		IBPTaskUIModel taskShapePackGoods = (IBPTaskUIModel) diagramManager.createDiagramElement(bpd, taskPackGoods);
		laneShapeDistribution.addChild(taskShapePackGoods);
		taskShapePackGoods.setBounds(400, 250, 80, 40);
		taskShapePackGoods.resetCaption();
		
		// Create Shop Goods task model and shape under Distribution lane
		IBPTask taskShipGoods = laneDistribution.createBPTask();
		taskShipGoods.setName("Ship Goods");
		IBPTaskUIModel taskShapeShipGoods = (IBPTaskUIModel) diagramManager.createDiagramElement(bpd, taskShipGoods);
		laneShapeDistribution.addChild(taskShapeShipGoods);
		taskShapeShipGoods.setBounds(530, 250, 80, 40);
		taskShapeShipGoods.resetCaption();
		
		// Create end event model and shape under Distribution lane
		IBPEndEvent endDistribution = laneDistribution.createBPEndEvent();
		IBPEndEventUIModel endShapeDistribution = (IBPEndEventUIModel) diagramManager.createDiagramElement(bpd, endDistribution);
		laneShapeDistribution.addChild(endShapeDistribution);
		endShapeDistribution.setBounds(660, 260, 20, 20);
		
		// Create sequence flow model between start event in Sales lane and Authorize Payment task
		IBPSequenceFlow seqSalesStartAuthorizePayment = IModelElementFactory.instance().createBPSequenceFlow();
		seqSalesStartAuthorizePayment.setFrom(startSales);
		seqSalesStartAuthorizePayment.setTo(taskAuthorizePayment);
		// Create sequence flow shape between start event in Sales lane and Authorize Payment task shape 
		diagramManager.createConnector(bpd, seqSalesStartAuthorizePayment, startShapeSales, taskShapeAuthorizePayment, new Point[] {new Point(90, 390), new Point(130, 390)});
		
		// Create sequence flow model and shape between Authorize Payment task and Process Order sub-process
		IBPSequenceFlow seqAuthorizePaymentProcessOrder = IModelElementFactory.instance().createBPSequenceFlow();
		seqAuthorizePaymentProcessOrder.setFrom(taskAuthorizePayment);
		seqAuthorizePaymentProcessOrder.setTo(subprocessProcessOrder);
		diagramManager.createConnector(bpd, seqAuthorizePaymentProcessOrder, taskShapeAuthorizePayment, subprocessShapeProcessOrder, new Point[] {new Point(210, 390), new Point(260, 390)});
		
		// Create sequence flow model and shape between Process Order sub-process and Pack Goods task
		IBPSequenceFlow seqProcessOrderPackGoods = IModelElementFactory.instance().createBPSequenceFlow();
		seqProcessOrderPackGoods.setFrom(subprocessProcessOrder);
		seqProcessOrderPackGoods.setTo(taskPackGoods);
		diagramManager.createConnector(bpd, seqProcessOrderPackGoods, subprocessShapeProcessOrder, taskShapePackGoods, new Point[] {new Point(350, 390), new Point(375, 390), new Point(375, 270), new Point(400, 270)});
		
		// Create sequence flow between Pack Goods task and Ship Goods task
		IBPSequenceFlow seqPackGoodsShipGoods = IModelElementFactory.instance().createBPSequenceFlow();
		seqPackGoodsShipGoods.setFrom(taskPackGoods);
		seqPackGoodsShipGoods.setTo(taskShipGoods);
		diagramManager.createConnector(bpd, seqPackGoodsShipGoods, taskShapePackGoods, taskShapeShipGoods, new Point[] {new Point(480, 270), new Point(530, 270)});
		
		// Create sequence flow between Ship Goods task and End Event in Distribution lane
		IBPSequenceFlow seqShipGoodsEnd = IModelElementFactory.instance().createBPSequenceFlow();
		seqShipGoodsEnd.setFrom(taskShipGoods);
		seqShipGoodsEnd.setTo(endDistribution);
		diagramManager.createConnector(bpd, seqShipGoodsEnd, taskShapeShipGoods, endShapeDistribution, new Point[] {new Point(610, 270), new Point(660, 270)});
		
		// Create Start Event model and shape in Financial Institution pool
		IBPStartEvent startFinancialInstitution = IModelElementFactory.instance().createBPStartEvent();
		IBPStartEventUIModel startShapeFinancialInstitution = (IBPStartEventUIModel) diagramManager.createDiagramElement(bpd, startFinancialInstitution);
		poolShapeFinancialInstitution.addChild(startShapeFinancialInstitution);
		startShapeFinancialInstitution.setBounds(70, 100, 20, 20);
		
		// Create Credit Card Authorization sub-process model and shape in Financial Institution pool
		IBPSubProcess subprocessCreditCardAuthorization = poolFinancialInstitution.createBPSubProcess();
		subprocessCreditCardAuthorization.setName("Credit Card Authorization");
		IBPSubProcessUIModel subprocessShapeCreditCardAuthorization = (IBPSubProcessUIModel) diagramManager.createDiagramElement(bpd, subprocessCreditCardAuthorization);
		poolShapeFinancialInstitution.addChild(subprocessShapeCreditCardAuthorization);
		subprocessShapeCreditCardAuthorization.setBounds(120, 85, 110, 50);
		subprocessShapeCreditCardAuthorization.resetCaption();
		
		// Create End Event model and shape in Financial Institution pool
		IBPEndEvent endFinancialInstitution = IModelElementFactory.instance().createBPEndEvent();
		IBPEndEventUIModel endShapeFinancialInstitution = (IBPEndEventUIModel) diagramManager.createDiagramElement(bpd, endFinancialInstitution);
		poolShapeFinancialInstitution.addChild(endShapeFinancialInstitution);
		endShapeFinancialInstitution.setBounds(280, 100, 20, 20);
		
		// Create sequence flow model and shape between Start Event in Financial Institution pool and Authorize Payment task 
		IBPSequenceFlow seqStartFinancialInstitutionCreditCardAuthorization = IModelElementFactory.instance().createBPSequenceFlow();
		seqStartFinancialInstitutionCreditCardAuthorization.setFrom(startFinancialInstitution);
		seqStartFinancialInstitutionCreditCardAuthorization.setTo(taskAuthorizePayment);
		diagramManager.createConnector(bpd, seqStartFinancialInstitutionCreditCardAuthorization, startShapeFinancialInstitution, subprocessShapeCreditCardAuthorization, new Point[] {new Point(90, 110), new Point(120, 110)});
		
		// Create sequence flow model and shape between Authorize Payment task and End Event in Financial Institution pool
		IBPSequenceFlow seqCreditCardAuthorizationEndFinancialInstitution = IModelElementFactory.instance().createBPSequenceFlow();
		seqCreditCardAuthorizationEndFinancialInstitution.setFrom(taskAuthorizePayment);
		seqCreditCardAuthorizationEndFinancialInstitution.setTo(endFinancialInstitution);
		diagramManager.createConnector(bpd, seqCreditCardAuthorizationEndFinancialInstitution, subprocessShapeCreditCardAuthorization, endShapeFinancialInstitution, new Point[] {new Point(230, 110), new Point(280, 110)});
		
		// Create message flow model and shape from Credit Card Authorization task to Credit Card Authorization sub-process 
		IBPMessageFlow msgAuthorizePaymentCreditCardAuthorization = IModelElementFactory.instance().createBPMessageFlow();
		msgAuthorizePaymentCreditCardAuthorization.setFrom(taskAuthorizePayment);
		msgAuthorizePaymentCreditCardAuthorization.setTo(subprocessCreditCardAuthorization);
		diagramManager.createConnector(bpd, msgAuthorizePaymentCreditCardAuthorization, taskShapeAuthorizePayment, subprocessShapeCreditCardAuthorization, new Point[] {new Point(150, 370), new Point(150, 135)});
		
		// Create message flow model and shape from Credit Card Authorization sub-process to Credit Card Authorization task
		IBPMessageFlow msgCreditCardAuthorizationAuthorizePayment = IModelElementFactory.instance().createBPMessageFlow();
		msgCreditCardAuthorizationAuthorizePayment.setFrom(subprocessCreditCardAuthorization);
		msgCreditCardAuthorizationAuthorizePayment.setTo(taskAuthorizePayment);
		diagramManager.createConnector(bpd, msgCreditCardAuthorizationAuthorizePayment, subprocessShapeCreditCardAuthorization, taskShapeAuthorizePayment, new Point[] {new Point(190, 135), new Point(190, 370)});
		
		// Show up the diagram
		diagramManager.openDiagram(bpd);
		
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}
