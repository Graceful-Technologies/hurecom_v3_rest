package com.gt.hurecom.service.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.ApplicationRequest;
import com.gt.hurecom.dto.recruitment.ApplicationResponse;
import com.gt.hurecom.dto.recruitment.ApplicationSearchRequest;
import com.gt.hurecom.exception.HurecomException;

public interface ApplicationService {

    ApplicationResponse createApplication(ApplicationRequest request);

    ApplicationResponse updateApplication(Long id, ApplicationRequest request) throws HurecomException;

    ApplicationResponse getApplication(Long id);

    PageResponse<ApplicationResponse> searchApplications(ApplicationSearchRequest request);

}
