package com.dropbox.v1.service;

import com.dropbox.exception.ApiException;
import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dropbox.client.ftp.FtpConnection.*;

@AllArgsConstructor
@Service
public class FtpService {

    private FTPClient ftp;

    public void createDirectory(String nameSector) {
        try {
            ftp = connectionFtp();
            ftp.makeDirectory(nameSector);
            closeConnectionFtp();
        } catch (IOException exception) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar diretorio");
        }
    }

    public void deleteDirectory(String nameDirectory) {
        try {
            ftp = connectionFtp();
            ftp.changeWorkingDirectory(nameDirectory);
            Arrays.stream(ftp.listNames())
                    .forEach(name -> {
                        try {
                            ftp.deleteFile(name);
                        } catch (ApiException | IOException e) {
                            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar arquivos do diretorio");
                        }
                    });

            ftp.changeWorkingDirectory("../");
            ftp.removeDirectory(nameDirectory);
            closeConnectionFtp();
        } catch (IOException exception) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar diretorio");
        }
    }

    public void fileUpload(String nameDirectory, MultipartFile file) {
        try {
            ftp = connectionFtp();
            targetingFtp(nameDirectory);
            ftp.storeFile(nameDirectory + file.getOriginalFilename(), file.getInputStream());
            closeConnectionFtp();
        } catch (ApiException | IOException exception) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao subir arquivo");
        }
    }

    public void deleteUpload(String nameDirectory, String nameFile) {
        try {
            ftp = connectionFtp();
            ftp.changeWorkingDirectory(nameDirectory);
            ftp.deleteFile(nameFile);
            closeConnectionFtp();

        } catch (ApiException | IOException exception) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar arquivo");
        }
    }

    public List<String> allUploads(String nameDirectory) {
        try {
            ftp = connectionFtp();
            ftp.changeWorkingDirectory(nameDirectory);

            List<String> allUploadsId = new ArrayList(Arrays.asList(ftp.listFiles()));

            closeConnectionFtp();

            return allUploadsId;
        } catch (ApiException | IOException exception) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar arquivos do diretorio " + nameDirectory);
        }
    }
}