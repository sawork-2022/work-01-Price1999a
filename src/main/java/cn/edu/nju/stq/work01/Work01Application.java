package cn.edu.nju.stq.work01;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class Work01Application {

    public static void main(String[] args) {
        //SpringApplication.run(Work01Application.class, args);
        //System.out.println(args.length);
        if (args.length == 1) {
            main1(args);
        } else if (args.length == 2) {
            main2(args);
        } else
            main0(args);
    }

    protected static void main0(String[] args) {
        System.out.println("这是不使用Spring框架的模式：");
        AsciiPanel myPanel1 = new AsciiPanel(80, 24, AsciiFont.TALRYTH_15_15);
        showWindows(myPanel1, "Non-Spring");
    }

    protected static void main1(String[] args) {
        System.out.println("这是使用Spring框架的XML配置方式的模式：");
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        //AsciiFont font = context.getBean("font1", AsciiFont.class);
        //System.out.println(AsciiFontToString(font));
        AsciiPanel myPanel1 = context.getBean("xml-panel1", AsciiPanel.class);
        showWindows(myPanel1, "Spring-XML CP437_8x8");
        AsciiPanel myPanel2 = context.getBean("xml-panel2", AsciiPanel.class);
        showWindows(myPanel2, "Spring-XML TALRYTH_15_15");
    }

    protected static void main2(String[] args) {
        System.out.println("这是使用Spring框架的Java配置方式的模式：");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsciiPanelConfig.class);
        AsciiPanel myPanel1 = context.getBean("java-panel1", AsciiPanel.class);
        AsciiPanel myPanel2 = context.getBean("java-panel2", AsciiPanel.class);
        //System.out.println(myPanel.getAsciiFont().getFontFilename());
        showWindows(myPanel1, "Spring-Java CP437_12x12");
        showWindows(myPanel2, "Spring-Java TAFFER_10x10");
    }

    protected static String AsciiFontToString(AsciiFont af) {
        return String.format("%s\t%d\t%d", af.getFontFilename(), af.getHeight(), af.getWidth());
    }

    protected static void showWindows(AsciiPanel myPanel, String s) {
        myPanel.write(s);
        JFrame jf = new JFrame("使用:\t" + s);
        jf.setBounds(300, 100, 400, 200);
        jf.add(myPanel);
        jf.setDefaultCloseOperation(2);
        jf.setVisible(true);
    }
}

@Configuration
class AsciiPanelConfig {
    @Bean(name = "java-font1")
    public AsciiFont CP437_12x12() {
        return new AsciiFont("cp437_12x12.png", 12, 12);
    }

    @Bean(name = "java-font2")
    public AsciiFont TAFFER_10x10() {
        return new AsciiFont("taffer_10x10.png", 10, 10);
    }

    @Bean(name = "java-panel1")
    public AsciiPanel myAsciiPanel1() {
        return new AsciiPanel(80, 24, CP437_12x12());
    }

    @Bean(name = "java-panel2")
    public AsciiPanel myAsciiPanel2() {
        return new AsciiPanel(80, 24, TAFFER_10x10());
    }
}
