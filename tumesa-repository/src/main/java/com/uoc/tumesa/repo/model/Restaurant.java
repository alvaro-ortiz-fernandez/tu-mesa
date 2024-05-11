package com.uoc.tumesa.repo.model;

import com.google.common.collect.Lists;
import com.mongodb.lang.Nullable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class Restaurant extends RepoEntity {

    private String name;
    private String description;
    private String address;
    private Images images;
    private List<Comment> comments;


    public Restaurant(String name, String description, String address, Images images, List<Comment> comments) {
        this(null, name, description, address, images, comments);
    }

    public Restaurant(@Nullable String id, String name, String description,
            String address, Images images, List<Comment> comments) {

        setId(id);
        this.name = name;
        this.description = description;
        this.address = address;
        this.images = images;
        this.comments = comments;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Images getImages() {
        return images;
    }
    public void setImages(Images images) {
        this.images = images;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public BigDecimal getRating() {
        if (comments == null || comments.isEmpty())
            return BigDecimal.ZERO;

        double average = comments.stream()
                .mapToDouble(comment -> comment.getRating().doubleValue())
                .average()
                .orElse(0);

        double integer = Math.floor(average);
        double decimal = average - integer;

        if (decimal < 0.25) {
            return BigDecimal.valueOf(integer);
        } else if (decimal < 0.75) {
            return BigDecimal.valueOf(integer + 0.5);
        } else {
            return BigDecimal.valueOf(integer + 1);
        }
    }


    public static class Images {

        private String main;
        private List<String> photos;

        public Images(String main) {
            this(main, Lists.newArrayList());
        }

        public Images(String main, List<String> photos) {
            this.main = main;
            this.photos = photos;
        }
        public String getMain() {
            return main;
        }
        public void setMain(String main) {
            this.main = main;
        }
        public List<String> getPhotos() {
            return photos;
        }
        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }
    }

    public static class Comment {

        private String user;
        private String content;
        private BigDecimal rating;
        private ZonedDateTime date;

        public Comment(String user, String content, BigDecimal rating, ZonedDateTime date) {
            this.user = user;
            this.content = content;
            this.rating = rating;
            this.date = date;
        }

        public String getUser() {
            return user;
        }
        public void setUser(String user) {
            this.user = user;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public BigDecimal getRating() {
            return rating;
        }
        public void setRating(BigDecimal rating) {
            this.rating = rating;
        }
        public ZonedDateTime getDate() {
            return date;
        }
        public void setDate(ZonedDateTime date) {
            this.date = date;
        }
    }
}
