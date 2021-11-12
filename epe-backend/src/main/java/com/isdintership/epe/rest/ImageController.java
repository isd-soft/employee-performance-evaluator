package com.isdintership.epe.rest;

import com.isdintership.epe.dto.ImageEditView;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;
import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ImageController {

    private final UserService userService;

    @PostMapping("/{id}")
    @RolesAllowed({ROLE_USER,ROLE_SYSADMIN})
    public ResponseEntity<ImageEditView> addImage(@RequestBody ImageEditView imageEditView,
                                                  @PathVariable(name = "id") String id) throws IOException {
        return new ResponseEntity<>(userService.uploadImage(imageEditView,id), HttpStatus.OK);
    }

    /*@GetMapping("/{id}")
    public Image*/
}
