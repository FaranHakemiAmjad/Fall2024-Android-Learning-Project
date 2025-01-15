package org.bihe.faranha.lowercaseimdb.utils;

import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;
import org.bihe.faranha.lowercaseimdb.data.objects.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ApplicationManager {

    static HashMap<Integer, User> users = new HashMap<>();
    static List<Report> allReports = new ArrayList<>();

//    public ApplicationManager() {
//        this.users = new HashMap<>();
//    }

    public User findUser(int id) {
        return users.get(id);
    }

//    public static User findUser(PreferencesManager preferencesManager,String email, String pass, String name, String gender, String id) {
//            if (
//                    preferencesManager.get(PreferencesManager.PREF_KEY_EMAIL, "").equals(email)
//                    && preferencesManager.get(PreferencesManager.PREF_KEY_PASSWORD,"").equals(pass)
//                    && preferencesManager.get(PreferencesManager.PREF_KEY_NAME, "").equals(name)
//                    && preferencesManager.get(PreferencesManager.PREF_KEY_GENDER, "").equals(gender)
//                    && preferencesManager.get(PreferencesManager.PREF_KEY_ID, "").equals(id)
//            ) {
//                return ;
//            }
//        return null;
//    }

//    public static void addUser(User user) {
//        users.put(user.getUserID(),user);
//    }

    public static void addReport(Report report) {
        allReports.add(report);
    }
//    public static HashMap<Integer,Report> getAllReports() {
//        for (User user : users.values()) {
//                allReports.putAll(user.getReports());
//        }
//        return allReports;
//    }

//    public static List<Report> getReports(String username) {
//        Report report1 = new Report(username,"9/11 Is Going To Happen Again!", "Quentin Tarantino has announced that he is going to make a movie about the tragic 9/11; however, instead of airplanes, he has scripted two giant feet instead!!", ReportCategory.MOVIES, Date.from(Instant.now()));
//        Report report2 = new Report(username, "What Is Her Guilty Pleasure?","Jenna Ortega Told Everyone That Her Guilty Pleasure Is None Of Anyone's Business. Sad News For Boys.",ReportCategory.CELEBS,Date.from(Instant.now()));
//        Report report3 = new Report(username, "Am I Invited?","Kanye West Asked Himself Before Going To Kim's Birthday.",ReportCategory.EVENTS, Date.from(Instant.now()));
//        Report report4 = new Report(username, "Who Is Your Daddy?", "Morgan Freeman Is Set To Launch A New Reality Show With Purpose Of Finding Black Men Real Fathers.", ReportCategory.SHOWS,Date.from(Instant.now()));
//        Report report5 = new Report(username, "HaHaHaHa", "Tom Cruise Laughed So Hard.",ReportCategory.NEWS,Date.from(Instant.now()));
//        Report report6 = new Report(username, "I Do That Because I Like That", "Timothée Chalamet Answered Critics After Being Asked So Much About The Reason Behind His Affair With That Kardashian.",ReportCategory.CELEBS,Date.from(Instant.now()));
//        Report report7 = new Report(username, "I Am Not Single, I Am On A Project!", "Jeniffer Lawrence Answered People While Being Drunk As Usual.",ReportCategory.MOVIES,Date.from(Instant.now()));
//        Report report8 = new Report(username, "The Top 10 Favourite Cartoons Of Jamie Foxx Has Been Revealed!", "I Love All Of TOM & JERRY's Episodes!",ReportCategory.CELEBS, Date.from(Instant.now()));
//        Report report9 = new Report(username, "The Anticipated Nolan's Movie Is Delayed!", "I Just Need To Lock In; Don't Worry Lads!", ReportCategory.MOVIES,Date.from(Instant.now()));
//        Report report10 = new Report(username, "Nothing Else Matters!", "Jimmy Fallon's Reaction After He Found Out About Mike Tyson's Defeat.",ReportCategory.SHOWS,Date.from(Instant.now()));
//        allReports.add(report1);
//        allReports.add(report2);
//        allReports.add(report3);
//        allReports.add(report4);
//        allReports.add(report5);
//        allReports.add(report6);
//        allReports.add(report7);
//        allReports.add(report8);
//        allReports.add(report9);
//        allReports.add(report10);
//        return allReports;
//    }

//    public static List<Report> filterByCategory(ReportCategory category) {
//        List<Report> filtered = new ArrayList<>();
//        for (Report report : ApplicationManager.allReports) {
//            if (report.getCategory() == category) {
//                filtered.add(report);
//            }
//        }
//        return filtered;
//    }
//
//    public static Report getReport(String title, String content) {
//        for (Report report : allReports) {
//            if (report.getTitle().equalsIgnoreCase(title) && report.getBody().equalsIgnoreCase(content)) {
//                return report;
//            }
//        }
//        return null;
//    }
}
