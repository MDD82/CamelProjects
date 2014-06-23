package com.camelprojects;

import java.awt.Choice;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class GetCsvByFTPSendXML {
	public static void main(String[] args) throws Exception {

		CamelContext cc = new DefaultCamelContext();
		cc.addRoutes(new RouteBuilder(){

			public void configure(){
				//polling ftp server each 10 seconds
				from("ftp://192.168.230.1?username=mauro&consumer.delay=10000")
				.choice()
				.when(header("CamelFileName").endsWith(".csv"))
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception
					{
						System.out.println("File CSV found!");
						System.out.println("Ciao");
						System.out.println("A tutti");
						System.out.println("Pluto");
					}
				})
				.to("file:C:/DataOut")
				.otherwise()
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception
					{
						System.out.println("File not managed on current version");

					}
				});
			}

		});

		cc.start();
		Thread.sleep(10000);
		cc.stop();

	}
}

