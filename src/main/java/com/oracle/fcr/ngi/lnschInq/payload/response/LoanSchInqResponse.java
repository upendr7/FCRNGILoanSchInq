package com.oracle.fcr.ngi.lnschInq.payload.response;

import com.oracle.fcr.ngi.payload.response.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoanSchInqResponse implements Response, Serializable {

	private static final long serialVersionUID = 7984978975120510419L;

	private String responseMessage;
	private String responseCode;

	@Override
	public String toString() {
		return "LoanSchInqResponse{" +
				"responseMessage='" + responseMessage + '\'' +
				", responseCode='" + responseCode + '\'' +
				'}';
	}
}
