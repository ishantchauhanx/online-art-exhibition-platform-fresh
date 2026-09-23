package com.artexhibit;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PresentationGenerator {
    public static void main(String[] args) throws IOException {
        String outputPath = args.length > 0 ? args[0] : "art-exhibition-presentation.pptx";

        try (XMLSlideShow ppt = new XMLSlideShow()) {
            addTitleSlide(ppt, "Online Art Exhibition Platform");
            addSlide(ppt, "Project Overview", Arrays.asList(
                "Java web application for art gallery management",
                "Role-based access for Admin, Artist, and Enthusiast",
                "Built using Servlets, JDBC, MySQL, JSP, and Bootstrap"
            ));
            addSlide(ppt, "Core Features", Arrays.asList(
                "User registration and login",
                "Artist artwork upload and dashboard",
                "Gallery browsing and purchase flow",
                "Admin approval workflow and role management"
            ));
            addSlide(ppt, "Database & Architecture", Arrays.asList(
                "MySQL tables: users, artworks, exhibitions, orders, feedback",
                "MVC pattern using Servlets, DAO classes, and JSP views",
                "Input validation, session management, and exception handling"
            ));
            addSlide(ppt, "Academic Evaluation", Arrays.asList(
                "Problem understanding and solution design",
                "Core Java concepts and OOP implementation",
                "JDBC integration and servlet/web integration",
                "Suitable for midterm project evaluation and demo"
            ));

            try (FileOutputStream out = new FileOutputStream(outputPath)) {
                ppt.write(out);
            }
        }

        System.out.println("Presentation created at: " + outputPath);
    }

    private static void addTitleSlide(XMLSlideShow ppt, String title) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextBox titleBox = slide.createTextBox();
        titleBox.setAnchor(new Rectangle2D.Double(100, 150, 700, 150));
        XSLFTextParagraph p = titleBox.addNewTextParagraph();
        XSLFTextRun run = p.addNewTextRun();
        run.setText(title);
        run.setFontSize(28.0);
        run.setBold(true);
        run.setFontColor(Color.DARK_GRAY);

        XSLFTextBox subtitle = slide.createTextBox();
        subtitle.setAnchor(new Rectangle2D.Double(150, 300, 500, 80));
        XSLFTextParagraph sp = subtitle.addNewTextParagraph();
        XSLFTextRun srun = sp.addNewTextRun();
        srun.setText("Guvi / Galgotias University - Java Web Project");
        srun.setFontSize(18.0);
        srun.setFontColor(new Color(50, 80, 120));
    }

    private static void addSlide(XMLSlideShow ppt, String title, List<String> items) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextBox titleBox = slide.createTextBox();
        titleBox.setAnchor(new Rectangle2D.Double(80, 40, 700, 60));
        XSLFTextParagraph tp = titleBox.addNewTextParagraph();
        XSLFTextRun tr = tp.addNewTextRun();
        tr.setText(title);
        tr.setFontSize(24.0);
        tr.setBold(true);
        tr.setFontColor(new Color(20, 70, 120));

        XSLFTextBox contentBox = slide.createTextBox();
        contentBox.setAnchor(new Rectangle2D.Double(100, 120, 600, 300));

        for (String item : items) {
            XSLFTextParagraph para = contentBox.addNewTextParagraph();
            para.setBullet(true);
            XSLFTextRun run = para.addNewTextRun();
            run.setText(item);
            run.setFontSize(18.0);
            run.setFontColor(new Color(30, 30, 30));
        }
    }
}
