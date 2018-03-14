package com.revature.demo.beans;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/*
 * Author: Devin Dellamano
 * Purpose: Look up table for the user's role
 * Additional Information: 
 * 		-Role 1 is for associates
 * 		-Role 2 is for trainers & QC
 * 		-Role 3 is for admins
 */

public enum Role {
    NONE, ASSOCIATE, TRAINER, ADMIN;


}