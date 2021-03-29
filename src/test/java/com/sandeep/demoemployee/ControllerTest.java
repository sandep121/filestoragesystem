//package com.sandeep.demoemployee;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jayway.jsonpath.JsonPath;
//import com.sandeep.demoemployee.entity.CrudeEmployee;
//import com.sandeep.demoemployee.entity.NewEmployee;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//@EnableWebMvc
//@WebAppConfiguration
//@ContextConfiguration(classes = {ConfidentialityPreservingFileStorage.class})
//public class ControllerTest extends AbstractTransactionalTestNGSpringContextTests
//{
//    @Autowired
//    WebApplicationContext context;
//    private MockMvc mvc;
//    @BeforeMethod
//    public void setUp()
//    {
//        mvc= MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//
//    /*************** test cases for post requests ****************/
//
//    @Test
//    public void createEmployeeTest() throws Exception                //post is working or not
//    {
//        CrudeEmployee employeePost=new CrudeEmployee(2,"wonder woman","intern");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
//        String resultOutput=result.getResponse().getContentAsString();
//        Assert.assertEquals(resultOutput,resultOutput);
//    }
//
//    @Test(priority = 1)
//    public void directorValidationForManager() throws Exception                //Assigning director with manager
//    {
//        CrudeEmployee employeePost=new CrudeEmployee(2,"wonder woman","Director");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void multipleDirector() throws Exception                             //Adding second Director
//    {
//        CrudeEmployee employeePost=new CrudeEmployee(null,"wonder woman","Director");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void noData () throws Exception                //Adding employee with no data
//    {
//        CrudeEmployee employeePost=new CrudeEmployee();
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void partialData () throws Exception                //Adding employee with partial data
//    {
//        CrudeEmployee employeePost=new CrudeEmployee(2,"wonder woman");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void invalidParentId () throws Exception                //Adding employee with non existing manager
//    {
//        CrudeEmployee employeePost=new CrudeEmployee(12,"wonder woman", "Lead");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void hierarchyViolation () throws Exception                //Adding employee with violating organisation hierarchy
//    {
//        CrudeEmployee employee=new CrudeEmployee(8,"wonder woman", "Lead");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//
//        employee.setDesignation("manager");
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(mapper.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void invalidDesignation () throws Exception                //Adding employee with non existing Designation
//    {
//        CrudeEmployee employeePost=new CrudeEmployee(12,"wonder woman", "Laead");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    /*********************** test case for delete employee ********************/
//
//    @Test
//    public void delInvalidId () throws Exception                //Deleting non existing employee
//    {
//        mvc.perform(MockMvcRequestBuilders.delete("/employees/121"))
//                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
//    }
//
//    @Test
//    public void delDirectorWithChild () throws Exception                //Deleting director with children
//    {
//        mvc.perform(MockMvcRequestBuilders.delete("/employees/1"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void delDirectorWithoutChild () throws Exception                //Deleting director with children
//    {
//
//        mvc.perform(MockMvcRequestBuilders.delete("/employees/1"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void delEmployee() throws Exception                //Deleting director without children
//    {
//        for(int i=10;i>0;i--)
//        {
//            mvc.perform(MockMvcRequestBuilders.delete("/employees/"+i))
//                    .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
//        }
//
//    }
//
//    @Test
//    public void addDirectorToEmptyDatabase() throws Exception                //Re-adding director to the empty database after removing all the employees
//    {
//        for(int i=10;i>0;i--)
//        {
//            mvc.perform(MockMvcRequestBuilders.delete("/employees/"+i))
//                    .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
//        }
//
//        CrudeEmployee employeePost=new CrudeEmployee(null,"wonder woman","Director");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
//
//    }
//
//    @Test
//    public void addManagerToEmptyDatabase() throws Exception                //Re-adding manager to the empty database after removing all the employees
//    {
//        for(int i=10;i>0;i--)
//        {
//            mvc.perform(MockMvcRequestBuilders.delete("/employees/"+i))
//                    .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
//        }
//
//        CrudeEmployee employeePost=new CrudeEmployee(null,"wonder woman","manager");
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonInput=mapper.writeValueAsString(employeePost);
//        mvc.perform(MockMvcRequestBuilders.post("/employees").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//
//    }
//
//    ///////////////////////////////////////  putApi false /////////////////////////////////////
//    @Test
//    public void updateEmpInvalidId() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(2,"Rajat","Manager",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/13").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void updateEmpNoData() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(null,"","",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/2").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void updateEmpInvalidParId() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(12343,"Mohit","lead",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/2").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void updateEmpPromotion() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(1,"Mohit","Director",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/2").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void updateEmpDemotion() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(1,"Mohit","lead",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/21").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void updateEmpDemoteDirector() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(null,"","lead",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/1").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void updateEmpDirectorName() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(null,"Rajat","",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/1").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//    }
//    @Test
//    public void updateEmpDirectorWithDirector() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(null,"","Director",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/1").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//    }
//    @Test
//    public void updateEmpDirectorWithOutDirector() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(null,"","Manager",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/1").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void updateEmpDirectorParChange() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(2,"","",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/1").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
//    }
//    @Test
//    public void hulkChildOfCaptain() throws Exception
//    {
//        NewEmployee employee = new NewEmployee(4,"","",false);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/3").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//    }
//
//
//    /*********************************************************************/
//
//    @Test(priority = 1)
//    public void getAllTest() throws Exception
//    {
//        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/employees/"))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
//        String jsonOutput=result.getResponse().getContentAsString();
//        int length= JsonPath.parse(jsonOutput).read("$.length()");
//        Assert.assertTrue(length>0);
//    }
//    //Test for get Specific
//    @Test(priority = 2)
//    public void getUserTest() throws Exception
//    {
//        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/employees/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andReturn();
//        String jsonOutput=result.getResponse().getContentAsString();
//        int length= JsonPath.parse(jsonOutput).read("$.length()");
//        System.out.println(length);
//        Assert.assertTrue(length>0);
//    }
//    @Test(priority = 3)
//    public void getUserTestInvalidParent() throws Exception
//    {
//        mvc.perform(MockMvcRequestBuilders.get("/employees/11"))
//                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print()).andReturn();
//    }
//    @Test(priority = 4)
//    public void getUserTestNullParent() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/employees/null"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print()).andReturn();
//    }
//
//
//
//     @Test
//    public void updateEmpDemotionIron() throws Exception {
//        NewEmployee employee = new NewEmployee();
//        employee.setDesignation("Lead");
//        employee.setReplace(false);
//        //employee.setEmpName("Iron Man");
//        //employee.setManagerId(1);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = mapper.writeValueAsString(employee);
//        mvc.perform(MockMvcRequestBuilders.put("/employees/2").content(jsonInput).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
