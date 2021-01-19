package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class SiteStats {
    private String url;
    private int numVisits;

    /**
     * Constructor for the SiteStats class
     *
     * @param url
     *            String that represents an URL that the user has visited
     * @param numVisits
     *            An int that represents the number of times that the user has
     *            visited the url
     */
    public SiteStats(String url, int numVisits) {
        this.url = url;
        this.numVisits = numVisits;
    }

    /**
     * This method returns the number of times that the user has visited the url.
     *
     * @return An int that represents the number of times that the user has visited
     *         the url
     */
    public int getNumVisits() {
        return this.numVisits;
    }

    /**
     * This method returns the url that we are currently tracking
     *
     * @return A String that represents the url that we are currently tracking
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * This method updates the number of times that we have visited the url
     *
     * @param updatedNumVisits
     *            int that represents the number that we want to set numVisits to
     */
    public void setNumVisits(int updatedNumVisits) {
        this.numVisits = updatedNumVisits;
    }

    public String toString() {
        return this.url + " | " + this.numVisits;
    }

}

public class PartBSolution {

    private static Queue<SiteStats> sites = new LinkedList<SiteStats>();

    /**
     * sorted variable is defined to avoid sorting again and again when user without visiting new website
     * requests for top visited sites one more time
     */
    private static boolean sorted;

    private static void sort(Queue<SiteStats> sites) {
        for(int i =1; i<=sites.size();i++) {
            int maxIndex = getMaxIndex(sites,sites.size()-i);
           // System.out.println("MaxIndex is : "+maxIndex);
            insertMaxAtEnd(sites,maxIndex);
            //System.out.println("After inserting max value ");
            //System.out.println(sites);
        }
        sorted = true;
    }

    private static int getMaxIndex(Queue<SiteStats> sites, int n) {
        int maxValue = Integer.MIN_VALUE;
        int maxIndex = -1;
        int siz = sites.size();
        for(int i =0 ;i<siz;i++) {
            SiteStats temp = sites.remove();
            if(temp.getNumVisits()>maxValue && i<=n) {
                maxValue = temp.getNumVisits();
                maxIndex = i;
                sites.add(temp);
            }
            else {
                sites.add(temp);
            }
        }
        return maxIndex;
    }
    private static void insertMaxAtEnd(Queue<SiteStats> sites, int maxIndex) {
        SiteStats value;
        SiteStats maxValue = new SiteStats("blank",-1);
        int siz = sites.size();
        for(int i=0;i<siz;i++) {
            value = sites.remove();
            if(i==maxIndex) {
                maxValue = value;
            }
            else {
                sites.add(value);
            }
        }
        sites.add(maxValue);
    }

    // Main method to list top n visited sites
    public static void listTopVisitedSites(Queue<SiteStats> sites, int n) {
        // WRITE CODE HERE
        if(!sites.isEmpty()) {

            int i;
            //System.out.println("In List top visited Function");
            if (sorted == false)
                sort(sites);

            for (i = 1; i <= n; i++) {
                SiteStats temp = sites.remove();
                System.out.println(temp.getUrl());
                sites.add(temp);
            }

            // System.out.println(sites);

            while (i <= sites.size()) {
                sites.add(sites.remove());
                i++;
            }
        }
    }

    // Method to find the website in the queue and increment the visited count by 1, adding new node in case website is not found
    public static void updateCount(String url) {
        // WRITE CODE HERE
        int newObj=0;
        for(int i=0;i<sites.size();i++) {
            SiteStats temp = sites.remove();
            if(url == temp.getUrl()) {
                newObj=1;
                temp.setNumVisits(temp.getNumVisits()+1);
                sites.add(temp);
                break;
            }
            else {
                sites.add(temp);
            }
        }
        if (newObj==0) {
            sites.add(new SiteStats(url,1));
        }
        sorted = false;
    }

    public static void main(String[] args) {
        String[] visitedSites = { "www.google.co.in", "www.google.co.in", "www.facebook.com", "www.upgrad.com", "www.google.co.in", "www.youtube.com",
                "www.facebook.com", "www.upgrad.com", "www.facebook.com", "www.google.co.in", "www.microsoft.com", "www.9gag.com", "www.netflix.com",
                "www.netflix.com", "www.9gag.com", "www.microsoft.com", "www.amazon.com", "www.amazon.com", "www.uber.com", "www.amazon.com",
                "www.microsoft.com", "www.upgrad.com","www.microsoft.com" };

        for (String url : visitedSites) {
            updateCount(url);
        }
       // System.out.println("Before executing any function");
        //System.out.println(sites);
        listTopVisitedSites(sites, 5);
        //System.out.println(sites);

    }

}
