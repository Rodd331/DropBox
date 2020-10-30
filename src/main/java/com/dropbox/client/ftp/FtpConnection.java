package com.dropbox.client.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class FtpConnection {

    static FTPClient ftp = new FTPClient();

    private FtpConnection() {
    }

    public static FTPClient connectionFtp() throws IOException {
        ftp.connect("172.17.0.2");
        ftp.login("rodd", "rodd123");
        return ftp;
    }

    public static boolean targetingFtp(String id) throws IOException {
        ftp.changeWorkingDirectory(id);
        return true;
    }

    public static boolean closeConnectionFtp() throws IOException {
        ftp.disconnect();
        return true;
    }
}