package newsfeed.response;

import com.google.gson.annotations.SerializedName;

public enum Status {
    @SerializedName("ok")
    OK,

    @SerializedName("error")
    ERROR
}
