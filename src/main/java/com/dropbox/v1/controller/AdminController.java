package com.dropbox.v1.controller;

import com.dropbox.v1.service.FtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "Controller FTP")
@AllArgsConstructor
@RequestMapping("/v1/admin")
@RestController
public class AdminController {

    private final FtpService ftpService;

    @ApiOperation(value = "Deleta um diretorio e seus arquivos.")
    @DeleteMapping("/directory/{nameDirectory}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Deleted Directory and files."),
            @ApiResponse(code = 401, message = "Unauthorized Method."),
            @ApiResponse(code = 403, message = "Method not allowed."),
            @ApiResponse(code = 404, message = "Directory not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDirectory(@PathVariable String nameDirectory) {
        ftpService.deleteDirectory(nameDirectory);
    }

    @ApiOperation(value = "Cria um diretorio.")
    @PostMapping("/directory/{nameDirectory}")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Create Directory."),
            @ApiResponse(code = 401, message = "Unauthorized Method."),
            @ApiResponse(code = 403, message = "Method not allowed."),
            @ApiResponse(code = 404, message = "Directory already exists."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void createDirectory(@PathVariable String nameDirectory) {
        ftpService.createDirectory(nameDirectory);
    }

    @ApiOperation(value = "Faz upload de um arquivo.")
    @PostMapping("/file/{nameDirectory}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful Operation."),
            @ApiResponse(code = 401, message = "Unauthorized Method."),
            @ApiResponse(code = 403, message = "Method not allowed."),
            @ApiResponse(code = 404, message = "Directory not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    public void fileUpload(@PathVariable String nameDirectory, @RequestParam MultipartFile file) {
        ftpService.fileUpload(nameDirectory, file);
    }

    @ApiOperation(value = "Deleta um arquivo.")
    @DeleteMapping("/directory/{nameDirectory}/file/{nameFile}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Deleted file."),
            @ApiResponse(code = 401, message = "Unauthorized Method."),
            @ApiResponse(code = 403, message = "Method not allowed."),
            @ApiResponse(code = 404, message = "File not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUpload(@PathVariable String nameDirectory, @PathVariable String nameFile) {
        ftpService.deleteUpload(nameDirectory, nameFile);
    }

    @ApiOperation(value = "Retorna uma lista com todos uploads feitos.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 401, message = "Unauthorized Method"),
            @ApiResponse(code = 403, message = "Method not allowed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/directory/{nameDirectory}/")
    public List<String> allUploads(@PathVariable String nameDirectory) {
        return ftpService.allUploads(nameDirectory);
    }
}