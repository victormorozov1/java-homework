package edu.phystech.hw2.contact;

record Contact(String username, String email)  implements Comparable<Contact> {
    public static final String UNKNOWN_EMAIL = "unknown";

    Contact {
        if (username.replaceAll(" ", "").isEmpty()) {
            throw new InvalidContactFieldException("username");
        }
        if (!email.endsWith("@gmail.com") & !email.equals(UNKNOWN_EMAIL)) {
            throw new InvalidContactFieldException("email");
        }
    }

    Contact(String username) { this(username, UNKNOWN_EMAIL); }


    public int compareTo(Contact o) {
        if (this.username.length() > o.username.length()) {
            return 1;
        } else if (this.username.length() == o.username.length()) {
            return 0;
        }
        return -1;
    }
}