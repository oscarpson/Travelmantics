package com.oscar.Travelmantics.model;

/**
 * The type Deal model.
 */
public class DealModel {
    /**
     * The Deal title.
     */
    String dealTitle;
    /**
     * The Deal description.
     */
    String dealDescription;
    /**
     * The Price.
     */
    String price;
    /**
     * The Image url.
     */
    String imageUrl;

    /**
     * Instantiates a new Deal model.
     */
    public DealModel() {
    }

    /**
     * Instantiates a new Deal model.
     *
     * @param dealTitle       the deal title
     * @param dealDescription the deal description
     * @param price           the price
     * @param imageUrl        the image url
     */
    public DealModel(String dealTitle, String dealDescription, String price, String imageUrl) {
        this.dealTitle = dealTitle;
        this.dealDescription = dealDescription;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * Gets deal title.
     *
     * @return the deal title
     */
    public String getDealTitle() {
        return dealTitle;
    }

    /**
     * Sets deal title.
     *
     * @param dealTitle the deal title
     */
    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    /**
     * Gets deal description.
     *
     * @return the deal description
     */
    public String getDealDescription() {
        return dealDescription;
    }

    /**
     * Sets deal description.
     *
     * @param dealDescription the deal description
     */
    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets image url.
     *
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets image url.
     *
     * @param imageUrl the image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
