//package se.inera.fmu.application;
//
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.test.ActivitiRule;
//import org.activiti.engine.test.Deployment;
//import org.junit.Rule;
//import org.junit.Test;
//import se.inera.fmu.application.util.EavropUtil;
//import se.inera.fmu.domain.model.eavrop.Eavrop;
//
//import java.util.HashMap;
//
//import static org.junit.Assert.assertNotNull;
//
///**
// * Created by Rasheed on 8/7/14.
// *
// * fmu-process unit test!
// */
//public class SimpleFmuProcessTest {
//
//    @Rule
//    public ActivitiRule activitiRule = new ActivitiRule();
//
//    @Test
//    @Deployment(resources = {"processes/fmu.bpmn"})
//    public void test() {
//        Eavrop eavrop = EavropUtil.createEavrop();
//        String businessKey = eavrop.getArendeId().toString();
//
//        // Also set the eavrop as process-variable
//        HashMap<String, Object> variables = new HashMap<String, Object>();
//        variables.put("eavrop", eavrop);
//
//        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("fmuProcess",
//                                                                                                businessKey, variables);
//        assertNotNull(processInstance);
//    }
//}
