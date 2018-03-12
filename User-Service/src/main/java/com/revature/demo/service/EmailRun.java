package com.revature.demo.service;

import com.revature.demo.beans.BamUser;

public class EmailRun implements Runnable {
  private BamUser user;

  public EmailRun() {
    // Empty because of noargs constructor
  }

  public BamUser getUser() {
    return user;
  }

  public void setUser(BamUser user2) {
    this.user = user2;
  }

  @Override
  public void run() {
      MailService.sendMail(user.getEmail(), user.getPwd());
  }

}