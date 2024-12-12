package com.escolinha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.escolinha.view.home.HomePage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
public class EscolinhaSaaSApplication {

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        ConfigurableApplicationContext context = SpringApplication.run(EscolinhaSaaSApplication.class, args);
        }

    @EventListener(ApplicationReadyEvent.class)
    public void startSwing() {

        SwingUtilities.invokeLater(()->{
            HomePage homePage = appContext.getBean(HomePage.class);
            homePage.setVisible(true);
        });
    }
}
