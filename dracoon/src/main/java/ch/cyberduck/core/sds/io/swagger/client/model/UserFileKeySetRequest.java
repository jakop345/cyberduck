/*
 * DRACOON
 * Version 4.4.0 - built at: 2017-12-04 04:14:43, API server: https://demo.dracoon.com/api/v4
 *
 * OpenAPI spec version: 4.4.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ch.cyberduck.core.sds.io.swagger.client.model;

import java.util.Objects;
import ch.cyberduck.core.sds.io.swagger.client.model.FileKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * UserFileKeySetRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-03T10:55:56.129+02:00")
public class UserFileKeySetRequest {
  @JsonProperty("fileId")
  private Long fileId = null;

  @JsonProperty("userId")
  private Long userId = null;

  @JsonProperty("fileKey")
  private FileKey fileKey = null;

  public UserFileKeySetRequest fileId(Long fileId) {
    this.fileId = fileId;
    return this;
  }

   /**
   * File Id
   * @return fileId
  **/
  @ApiModelProperty(required = true, value = "File Id")
  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public UserFileKeySetRequest userId(Long userId) {
    this.userId = userId;
    return this;
  }

   /**
   * User Id
   * @return userId
  **/
  @ApiModelProperty(required = true, value = "User Id")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public UserFileKeySetRequest fileKey(FileKey fileKey) {
    this.fileKey = fileKey;
    return this;
  }

   /**
   * Filekeys
   * @return fileKey
  **/
  @ApiModelProperty(required = true, value = "Filekeys")
  public FileKey getFileKey() {
    return fileKey;
  }

  public void setFileKey(FileKey fileKey) {
    this.fileKey = fileKey;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserFileKeySetRequest userFileKeySetRequest = (UserFileKeySetRequest) o;
    return Objects.equals(this.fileId, userFileKeySetRequest.fileId) &&
        Objects.equals(this.userId, userFileKeySetRequest.userId) &&
        Objects.equals(this.fileKey, userFileKeySetRequest.fileKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileId, userId, fileKey);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserFileKeySetRequest {\n");
    
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    fileKey: ").append(toIndentedString(fileKey)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

