package com.pandar.framework.file.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocalFile {

    @NotBlank(message = "本地存储路径不能为空")
    private String storagePath;

}
