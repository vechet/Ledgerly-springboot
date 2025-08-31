package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumGlobalParam;
import com.vechetchuo.Ledgerly.enums.EnumGlobalParamType;
import com.vechetchuo.Ledgerly.mappings.CategoryMapper;
import com.vechetchuo.Ledgerly.models.domains.Category;
import com.vechetchuo.Ledgerly.models.domains.AuditLog;
import com.vechetchuo.Ledgerly.models.dtos.category.*;
import com.vechetchuo.Ledgerly.repositories.CategoryRepository;
import com.vechetchuo.Ledgerly.repositories.AuditLogRepository;
import com.vechetchuo.Ledgerly.repositories.GlobalParamRepository;
import com.vechetchuo.Ledgerly.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private GlobalParamRepository globalParamRepository;

    @Autowired
    private CategoryMapper mapper;

    public ApiResponse<GetCategoryResponse> getCategory(GetCategoryRequest req){
        try{
            // get current Category
            var currentCategory = categoryRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentCategory == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // Response
            var res = mapper.toGetDto(currentCategory);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public ApiResponse<GetCategoriesResponse> getCategories(PaginationRequest req){
        try{
            PageRequest pageRequest = PaginationUtil.toPageRequest(req);
            Page<Category> categoryPage = categoryRepository.findDynamic(req.getFilter().getSearch(), pageRequest);
            var categories = categoryPage.getContent().stream().map(mapper::toGetsDto).toList();
            var pageInfo = new PageInfo(req.getPage(), req.getPageSize(), categoryPage.getTotalPages(), categoryPage.getTotalElements());

            // Response
            var res = new GetCategoriesResponse(categories, pageInfo);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<CreateCategoryResponse> createCategory(CreateCategoryRequest req){
        try{
            //get userId

            // get some infos
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Normal.getMessage(), EnumGlobalParamType.CategoryxxxStatus.getMessage());
            var parent = categoryRepository.findById(req.getParentId()).orElse(null);

            // mapping dto to entity
            var newCategory = mapper.toCreateEntity(req);
            newCategory.setParent(parent);
            newCategory.setGlobalParam(status);
            newCategory.setUserId("1");
            newCategory.setCreatedBy("1");
            newCategory.setCreatedDate(LocalDateTime.now());

            // add new category
            categoryRepository.save(newCategory);

            // Add audit log
            var categoryAuditLog = new AuditLog();
            categoryAuditLog.setControllerName("Category");
            categoryAuditLog.setMethodName("Create");
            categoryAuditLog.setTransactionId(newCategory.getId());
            categoryAuditLog.setTransactionNo(newCategory.getName());
            categoryAuditLog.setDescription(GetAuditDescription(newCategory.getId()));
            categoryAuditLog.setCreatedBy("1");
            categoryAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(categoryAuditLog);

            // Response
            var res = mapper.toCreateDto(newCategory);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<UpdateCategoryResponse> updateCategory(UpdateCategoryRequest req){
        try{
            //get userId

            // get current category
            var currentCategory = categoryRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentCategory == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // get some infos
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Deleted.getMessage(), EnumGlobalParamType.CategoryxxxStatus.getMessage());
            var parent = categoryRepository.findById(req.getParentId()).orElse(null);

            // Check if the category record is already deleted
            if (currentCategory.getGlobalParam().getId() == status.getId())
            {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.ALREADY_DELETE));
                return ApiResponse.failure(ApiResponseStatus.ALREADY_DELETE);
            }

            // update current category
            currentCategory.setName(req.getName());
            currentCategory.setParent(parent);
            currentCategory.setMemo(req.getMemo());
            currentCategory.setUserId("1");
            currentCategory.setModifiedBy("1");
            currentCategory.setModifiedDate(LocalDateTime.now());
            categoryRepository.save(currentCategory);

            // Add audit log
            var categoryAuditLog = new AuditLog();
            categoryAuditLog.setControllerName("Category");
            categoryAuditLog.setMethodName("Update");
            categoryAuditLog.setTransactionId(currentCategory.getId());
            categoryAuditLog.setTransactionNo(currentCategory.getName());
            categoryAuditLog.setDescription(GetAuditDescription(currentCategory.getId()));
            categoryAuditLog.setCreatedBy("1");
            categoryAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(categoryAuditLog);

            // Response
            var res = mapper.toUpdateDto(currentCategory);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<DeleteCategoryResponse> deleteCategory(DeleteCategoryRequest req){
        try{
            //get userId

            // get current category
            var currentCategory = categoryRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentCategory == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // get status
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Deleted.getMessage(), EnumGlobalParamType.CategoryxxxStatus.getMessage());

            // Check if the category record is already deleted
            if (currentCategory.getGlobalParam().getId() == status.getId())
            {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.ALREADY_DELETE));
                return ApiResponse.failure(ApiResponseStatus.ALREADY_DELETE);
            }

            // update current category
            currentCategory.setGlobalParam(status);
            currentCategory.setUserId("1");
            currentCategory.setModifiedBy("1");
            currentCategory.setModifiedDate(LocalDateTime.now());
            categoryRepository.save(currentCategory);

            // Add audit log
            var categoryAuditLog = new AuditLog();
            categoryAuditLog.setControllerName("Category");
            categoryAuditLog.setMethodName("Delete");
            categoryAuditLog.setTransactionId(currentCategory.getId());
            categoryAuditLog.setTransactionNo(currentCategory.getName());
            categoryAuditLog.setDescription(GetAuditDescription(currentCategory.getId()));
            categoryAuditLog.setCreatedBy("1");
            categoryAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(categoryAuditLog);

            // Response
            return ApiResponse.success(null);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public String GetAuditDescription(int id){
        var category = categoryRepository.findById(id).orElse(null);
        var recordAuditLogCategory = mapper.toAuditLogDto(category);
        return JsonConverterUtils.SerializeObject(recordAuditLogCategory);
    }
}
