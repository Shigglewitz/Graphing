package org.shigglewitz.chess.controller;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class Deployments {
    private static WebArchive controllerWar;
    private static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment(testable = false, name = "controller", managed = true, order = 1)
    @TargetsContainer("tomcat")
    public static WebArchive createControllerDeployment() {
        if (controllerWar != null) {
            return controllerWar;
        }

        controllerWar = ShrinkWrap.create(WebArchive.class, "Chess.war");
        controllerWar.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
        controllerWar.addPackage(Package
                .getPackage("org.shigglewitz.chess.controller"));
        controllerWar
                .addAsWebResource(new File(WEBAPP_SRC, "jsp/viewChess.jsp"));
        // .importFrom(new File("w1-controller.war")).as(WebArchive.class);

        return controllerWar;
    }
}
