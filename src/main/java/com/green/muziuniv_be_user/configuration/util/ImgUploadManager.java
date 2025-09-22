package com.green.muziuniv_be_user.configuration.util;

import com.green.muziuniv_be_user.configuration.constants.ConstFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImgUploadManager {
    private final ConstFile constFile;
    private final FileUtils fileUtils;

    private String makeProfileDirectoryPath(long userId) {
        return String.format("%s/%d", constFile.getDirectory(), userId);
    }

    //프로파일 유저 폴더 삭제
    public void removeProfileDirectory(long userId) {
        String directory = makeProfileDirectoryPath(userId);
        fileUtils.deleteFolder(directory, true);
    }

    //저장한 파일명 리턴
    public String saveProfilePic(long userId, MultipartFile profilePicFile) {
        //폴더 생성
        String directory = makeProfileDirectoryPath(userId);
        fileUtils.makeFolders(directory);

        String randomFileName = fileUtils.makeRandomFileName(profilePicFile);
        String savePath = directory + "/" + randomFileName;

        try {
            fileUtils.transferTo(profilePicFile, savePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "프로파일 이미지 저장에 실패하였습니다.");
        }
        return randomFileName;
    }
}
