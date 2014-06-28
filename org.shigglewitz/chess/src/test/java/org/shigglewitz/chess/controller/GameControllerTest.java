package org.shigglewitz.chess.controller;

import java.net.URL;

import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shigglewitz.chess.entity.Game;
import org.shigglewitz.chess.entity.random.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@RunWith(Arquillian.class)
public class GameControllerTest {
    // http://localhost:8080/Chess/
    @ArquillianResource
    private URL deploymentUrl;

    @Autowired
    private RandomUtil randomUtil;

    @Test
    @OperateOnDeployment("controller")
    public void testViewGame() {
        Game game = this.randomUtil.createRandomDefaultGame();
        ResponseEntity<String> response = null;
        response = ControllerTestHelper.accessUrl(this.deploymentUrl, "game/"
                + game.getId(), HttpMethod.GET);
        this.printResponse(response);

    }

    private void printResponse(ResponseEntity<String> response) {
        String divider = "~~~~~~~~~";
        String headerDivider = "~HEADERS~";
        String bodyDivider = "~~~BODY~~";

        System.out.println(divider);
        System.out.println(headerDivider);
        for (String headerKey : response.getHeaders().keySet()) {
            System.out.println(headerKey + ": "
                    + response.getHeaders().getFirst(headerKey));
        }
        System.out.println(bodyDivider);
        System.out.println(response.getBody());
        System.out.println(divider);
    }
}
