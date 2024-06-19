package com.ejarestan.crawl_ejarestan;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class EjarestanScraper {

    private static final String BASE_URL = "https://www.ejarestan.ir/v/";
    private static final int START_ID = 444;
    private static final int END_ID = 55555555;

    public static void main(String[] args) {
        // MongoDB connection settings
        String connectionString = "mongodb://localhost:27017";
        try (var mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("ejarestan_db");
            MongoCollection<Document> collection = database.getCollection("posts");

            for (int pk = START_ID; pk <= END_ID; pk++) {
                String url = BASE_URL + pk + "/";
                try {
                    // Fetch and parse the HTML
                    org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

                    // Extract relevant information from the page
                    Element agahiBox = doc.selectFirst("#agahibox");
                    if (agahiBox != null) {
                        String title = agahiBox.selectFirst(".agahititle").text();
                        String timeElapsed = agahiBox.selectFirst(".agahitimeelapsed").text();
                        Element descriptionElement = agahiBox.select(".agahibodycol").last().child(agahiBox.select(".agahibodycol").last().children().size()-2);


                        String description = "";
                        if (descriptionElement!=null)
                        {
                            description=descriptionElement.text();


                        }

                        // Extract category, location, and image src
                        String category = "";
                        String location = "";
                        String srcImg = "";
                        String type = "";

                        String phoneNumber = "";

                        Elements infoRows = agahiBox.select(".agahiinforow");
                        for (Element row : infoRows) {
                            Element firstDiv = row.selectFirst("div:first-child");
                            Element secondDiv = row.selectFirst("div:last-child");
                            if (firstDiv != null && secondDiv != null) {
                                String key = firstDiv.text().trim();
                                String value = secondDiv.text().trim();
                                if (key.equals("دسته بندی"))
                                    category = value;
                                else if (key.equals("محل آگهی"))
                                    location = value;
                                else if (key.equals("نوع آگهی"))
                                    type = value;
                            }
                        }

                        // Extract image src
                        Element imgElement = agahiBox.selectFirst(".agahiimgboimg");
                        if (imgElement != null) {
                            srcImg = imgElement.attr("src");
                        }

                        Element phoneElement = doc.selectFirst("#agahicallnum > div:last-child");
                        if (phoneElement != null) {
                            phoneNumber = phoneElement.text().trim();
                        }

                        // Create a Post object
                        Post post = new Post();
                        post.setUrl(url);
                        post.setTitle(title);
                        post.setTimeElapsed(timeElapsed);
                        post.setDescription(description);
                        post.setCategory(category);
                        post.setLocation(location);
                        post.setSrcImg(srcImg);

                        post.setPk(pk); // Set the pk field as long

                        post.setPhoneNumber(phoneNumber);

                        post.setType(type);

                        // Convert Post to MongoDB Document and save
                        Document docPost = post.toDocument();
                        collection.insertOne(docPost);
                        System.out.println("Saved post with URL: " + url);

                        Thread.sleep(1000 * 4);
                    } else {
                        System.out.println("No agahibox found for URL: " + url);
                    }

                } catch (IOException e) {
                    System.err.println("Error fetching URL: " + url);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
