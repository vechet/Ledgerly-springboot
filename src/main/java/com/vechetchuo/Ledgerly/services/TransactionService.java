package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumGlobalParam;
import com.vechetchuo.Ledgerly.enums.EnumGlobalParamType;
import com.vechetchuo.Ledgerly.mappings.TransactionMapper;
import com.vechetchuo.Ledgerly.models.domains.Transaction;
import com.vechetchuo.Ledgerly.models.domains.AuditLog;
import com.vechetchuo.Ledgerly.models.dtos.transaction.*;
import com.vechetchuo.Ledgerly.repositories.*;
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
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private GlobalParamRepository globalParamRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionMapper mapper;

    public ApiResponse<GetTransactionResponse> getTransaction(GetTransactionRequest req){
        try{
            // get current Transaction
            var currentTransaction = transactionRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentTransaction == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // Response
            var res = mapper.toGetDto(currentTransaction);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public ApiResponse<GetTransactionsResponse> getTransactions(PaginationRequest req){
        try{
            PageRequest pageRequest = PaginationUtil.toPageRequest(req);
            Page<Transaction> transactionPage = transactionRepository.findDynamic(req.getFilter().getSearch(), pageRequest);
            var transactions = transactionPage.getContent().stream().map(mapper::toGetsDto).toList();
            var pageInfo = new PageInfo(req.getPage(), req.getPageSize(), transactionPage.getTotalPages(), transactionPage.getTotalElements());

            // Response
            var res = new GetTransactionsResponse(transactions, pageInfo);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<CreateTransactionResponse> createTransaction(CreateTransactionRequest req){
        try{
            //get userId

            // get some infos
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Normal.getMessage(), EnumGlobalParamType.TransactionxxxStatus.getMessage());
            var account = accountRepository.findById(req.getAccountId()).orElse(null);
            var category = categoryRepository.findById(req.getCategoryId()).orElse(null);

            // mapping dto to entity
            var newTransaction = mapper.toCreateEntity(req);
            newTransaction.setAccount(account);
            newTransaction.setCategory(category);
            newTransaction.setGlobalParam(status);
            newTransaction.setUserId("1");
            newTransaction.setCreatedBy("1");
            newTransaction.setCreatedDate(LocalDateTime.now());

            // add new transaction
            transactionRepository.save(newTransaction);

            // Add audit log
            var transactionAuditLog = new AuditLog();
            transactionAuditLog.setControllerName("Transaction");
            transactionAuditLog.setMethodName("Create");
            transactionAuditLog.setTransactionId(newTransaction.getId());
            transactionAuditLog.setTransactionNo("");
            transactionAuditLog.setDescription(GetAuditDescription(newTransaction.getId()));
            transactionAuditLog.setCreatedBy("1");
            transactionAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(transactionAuditLog);

            // Response
            var res = mapper.toCreateDto(newTransaction);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<UpdateTransactionResponse> updateTransaction(UpdateTransactionRequest req){
        try{
            //get userId

            // get current transaction
            var currentTransaction = transactionRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentTransaction == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // get status
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Deleted.getMessage(), EnumGlobalParamType.TransactionxxxStatus.getMessage());
            var account = accountRepository.findById(req.getAccountId()).orElse(null);
            var category = categoryRepository.findById(req.getCategoryId()).orElse(null);

            // Check if the transaction record is already deleted
            if (currentTransaction.getGlobalParam().getId() == status.getId())
            {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.ALREADY_DELETE));
                return ApiResponse.failure(ApiResponseStatus.ALREADY_DELETE);
            }

            // update current transaction
            currentTransaction.setAccount(account);
            currentTransaction.setCategory(category);
            currentTransaction.setAmount(req.getAmount());
            currentTransaction.setTransactionDate(req.getTransactionDate());
            currentTransaction.setType(req.getType());
            currentTransaction.setMemo(req.getMemo());
            currentTransaction.setUserId("1");
            currentTransaction.setModifiedBy("1");
            currentTransaction.setModifiedDate(LocalDateTime.now());
            transactionRepository.save(currentTransaction);

            // Add audit log
            var transactionAuditLog = new AuditLog();
            transactionAuditLog.setControllerName("Transaction");
            transactionAuditLog.setMethodName("Update");
            transactionAuditLog.setTransactionId(currentTransaction.getId());
            transactionAuditLog.setTransactionNo("");
            transactionAuditLog.setDescription(GetAuditDescription(currentTransaction.getId()));
            transactionAuditLog.setCreatedBy("1");
            transactionAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(transactionAuditLog);

            // Response
            var res = mapper.toUpdateDto(currentTransaction);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<DeleteTransactionResponse> deleteTransaction(DeleteTransactionRequest req){
        try{
            //get userId

            // get current transaction
            var currentTransaction = transactionRepository.findById(req.getId()).orElse(null);

            // check if brand not exists
            if (currentTransaction == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // get status
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Deleted.getMessage(), EnumGlobalParamType.TransactionxxxStatus.getMessage());

            // Check if the transaction record is already deleted
            if (currentTransaction.getGlobalParam().getId() == status.getId())
            {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.ALREADY_DELETE));
                return ApiResponse.failure(ApiResponseStatus.ALREADY_DELETE);
            }

            // update current transaction
            currentTransaction.setGlobalParam(status);
            currentTransaction.setUserId("1");
            currentTransaction.setModifiedBy("1");
            currentTransaction.setModifiedDate(LocalDateTime.now());
            transactionRepository.save(currentTransaction);

            // Add audit log
            var transactionAuditLog = new AuditLog();
            transactionAuditLog.setControllerName("Transaction");
            transactionAuditLog.setMethodName("Delete");
            transactionAuditLog.setTransactionId(currentTransaction.getId());
            transactionAuditLog.setTransactionNo("");
            transactionAuditLog.setDescription(GetAuditDescription(currentTransaction.getId()));
            transactionAuditLog.setCreatedBy("1");
            transactionAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(transactionAuditLog);

            // Response
            return ApiResponse.success(null);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public String GetAuditDescription(int id){
        var transaction = transactionRepository.findById(id).orElse(null);
        var recordAuditLogTransaction = mapper.toAuditLogDto(transaction);
        return JsonConverterUtils.SerializeObject(recordAuditLogTransaction);
    }
}
