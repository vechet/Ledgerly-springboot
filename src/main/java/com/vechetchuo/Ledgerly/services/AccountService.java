package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumGlobalParam;
import com.vechetchuo.Ledgerly.enums.EnumGlobalParamType;
import com.vechetchuo.Ledgerly.mappings.AccountMapper;
import com.vechetchuo.Ledgerly.models.domains.Account;
import com.vechetchuo.Ledgerly.models.domains.AuditLog;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.repositories.AccountRepository;
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
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired private AccountRepository accountRepository;
    @Autowired private AuditLogRepository auditLogRepository;
    @Autowired private GlobalParamRepository globalParamRepository;
    @Autowired private AccountMapper mapper;
    @Autowired UserService userService;

    public ApiResponse<GetAccountResponse> getAccount(GetAccountRequest req){
        try{
            // get current Account
            var currentAccount = accountRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentAccount == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // Response
            var res = mapper.toGetDto(currentAccount);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public ApiResponse<GetAccountsResponse> getAccounts(PaginationRequest req){
        try{
            PageRequest pageRequest = PaginationUtil.toPageRequest(req);
            Page<Account> accountPage = accountRepository.findDynamic(req.getFilter().getSearch(), pageRequest);
            var accounts = accountPage.getContent().stream().map(mapper::toGetsDto).toList();
            var pageInfo = new PageInfo(req.getPage(), req.getPageSize(), accountPage.getTotalPages(), accountPage.getTotalElements());

            // Response
            var res = new GetAccountsResponse(accounts, pageInfo);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<CreateAccountResponse> createAccount(CreateAccountRequest req){
        try{
            //get userId
            var userId = userService.getUserId();

            // get status
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Normal.getMessage(), EnumGlobalParamType.AccountxxxStatus.getMessage());

            // mapping dto to entity and add new account
            var newAccount = mapper.toCreateEntity(req);
            newAccount.setGlobalParam(status);
            newAccount.setUserId("1");
            newAccount.setCreatedBy("1");
            newAccount.setCreatedDate(LocalDateTime.now());
            accountRepository.save(newAccount);

            // Add audit log
            var accountAuditLog = new AuditLog();
            accountAuditLog.setControllerName("Account");
            accountAuditLog.setMethodName("Create");
            accountAuditLog.setTransactionId(newAccount.getId());
            accountAuditLog.setTransactionNo(newAccount.getName());
            accountAuditLog.setDescription(GetAuditDescription(newAccount.getId()));
            accountAuditLog.setCreatedBy("1");
            accountAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(accountAuditLog);

            // Response
            var res = mapper.toCreateDto(newAccount);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<UpdateAccountResponse> updateAccount(UpdateAccountRequest req){
        try{
            //get userId

            // get current account
            var currentAccount = accountRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentAccount == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // get status
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Deleted.getMessage(), EnumGlobalParamType.AccountxxxStatus.getMessage());

            // Check if the account record is already deleted
            if (currentAccount.getGlobalParam().getId() == status.getId())
            {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.ALREADY_DELETE));
                return ApiResponse.failure(ApiResponseStatus.ALREADY_DELETE);
            }

            // update current account
            currentAccount.setName(req.getName());
            currentAccount.setCurrency(req.getCurrency());
            currentAccount.setMemo(req.getMemo());
            currentAccount.setUserId("1");
            currentAccount.setModifiedBy("1");
            currentAccount.setModifiedDate(LocalDateTime.now());
            accountRepository.save(currentAccount);

            // Add audit log
            var accountAuditLog = new AuditLog();
            accountAuditLog.setControllerName("Account");
            accountAuditLog.setMethodName("Update");
            accountAuditLog.setTransactionId(currentAccount.getId());
            accountAuditLog.setTransactionNo(currentAccount.getName());
            accountAuditLog.setDescription(GetAuditDescription(currentAccount.getId()));
            accountAuditLog.setCreatedBy("1");
            accountAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(accountAuditLog);

            // Response
            var res = mapper.toUpdateDto(currentAccount);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<DeleteAccountResponse> deleteAccount(DeleteAccountRequest req){
        try{
            //get userId

            // get current account
            var currentAccount = accountRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentAccount == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // get status
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Deleted.getMessage(), EnumGlobalParamType.AccountxxxStatus.getMessage());

            // Check if the account record is already deleted
            if (currentAccount.getGlobalParam().getId() == status.getId())
            {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.ALREADY_DELETE));
                return ApiResponse.failure(ApiResponseStatus.ALREADY_DELETE);
            }

            // update current account
            currentAccount.setGlobalParam(status);
            currentAccount.setUserId("1");
            currentAccount.setModifiedBy("1");
            currentAccount.setModifiedDate(LocalDateTime.now());
            accountRepository.save(currentAccount);

            // Add audit log
            var accountAuditLog = new AuditLog();
            accountAuditLog.setControllerName("Account");
            accountAuditLog.setMethodName("Delete");
            accountAuditLog.setTransactionId(currentAccount.getId());
            accountAuditLog.setTransactionNo(currentAccount.getName());
            accountAuditLog.setDescription(GetAuditDescription(currentAccount.getId()));
            accountAuditLog.setCreatedBy("1");
            accountAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(accountAuditLog);

            // Response
            return ApiResponse.success(null);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public String GetAuditDescription(int id){
        var account = accountRepository.findById(id).orElse(null);
        var recordAuditLogAccount = mapper.toAuditLogDto(account);
        return JsonConverterUtils.SerializeObject(recordAuditLogAccount);
    }
}
