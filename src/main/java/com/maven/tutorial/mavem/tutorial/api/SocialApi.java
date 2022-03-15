package com.maven.tutorial.mavem.tutorial.api;

import com.maven.tutorial.mavem.tutorial.model.entity.Social;
import com.maven.tutorial.mavem.tutorial.model.response.SocialResponse;
import com.maven.tutorial.mavem.tutorial.service.SocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("api/v1/social")
@RestController
@AllArgsConstructor
public class SocialApi {
    private final SocialService service;

    @GetMapping()
    @Operation(summary = "Get social", responses = {
            @ApiResponse(description = "Get social success", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Page.class))),
            @ApiResponse(description = "Social not found",responseCode = "409",content = @Content)
    })
    public ResponseEntity<Page<SocialResponse>> findAll() {
        Page<SocialResponse> socialPage = service.findAll();
        return ResponseEntity.ok().body(socialPage);
    }

}
