package com.epa.epa;

import static com.epa.epa.authentication.AuthenticationConstants.KEY;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.epa.epa.authentication.AuthenticationService;
import com.epa.epa.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class ComponentTest {

  @Autowired
  protected ObjectMapper mapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  protected MockMvc mockMvc;

  @MockBean
  protected UserRepository userRepository;

  @MockBean
  protected AuthenticationService authenticationService;


  private ZonedDateTime now = ZonedDateTime.now();

  protected String jwt = Jwts.builder()
      .setClaims(Jwts.claims()
          .setIssuedAt(Date.from(now.toInstant()))
          .setExpiration(Date.from(now.plusMinutes(5).toInstant())))
      .claim("identity", "123435678")
      .claim("firstName", "Bruce")
      .claim("balance", "1000")
      .signWith(SignatureAlgorithm.HS512, KEY)
      .compact();

  @Before
  public void createMockMvc() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected String json(Object o) throws JsonProcessingException {
    return mapper.writeValueAsString(o);
  }

}