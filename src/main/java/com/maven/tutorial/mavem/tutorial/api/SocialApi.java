package com.maven.tutorial.mavem.tutorial.api;

import com.maven.tutorial.mavem.tutorial.model.response.SocialResponse;
import com.maven.tutorial.mavem.tutorial.service.SocialService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/social")
@RestController
@AllArgsConstructor
public class SocialApi {
    private final SocialService service;

    @GetMapping()
    @ApiOperation(value = "get all social")
    public ResponseEntity<Page<SocialResponse>> findAll() {
        Page<SocialResponse> socialPage = service.findAll();
        return ResponseEntity.ok().body(socialPage);
    }

}
