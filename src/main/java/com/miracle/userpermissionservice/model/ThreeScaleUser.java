package com.miracle.userpermissionservice.model;

public class ThreeScaleUser {
    ThreeScaleUser(){};
    ThreeScaleUser(int id, int account_id, String email){
            this.id = id;
            this.account_id = account_id;
            this.email = email;
        }

        private String email;
        private int id;
        private int account_id;

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAccount_id() {
            return account_id;
        }

        public int getId() {
            return id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
}
