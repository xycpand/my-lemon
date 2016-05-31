package com.mossle.user.service;

import java.io.ByteArrayOutputStream;

import javax.activation.DataSource;

import javax.annotation.Resource;

import com.mossle.api.store.StoreConnector;
import com.mossle.api.store.StoreDTO;

import com.mossle.core.store.ByteArrayDataSource;

import com.mossle.user.ImageUtils;
import com.mossle.user.persistence.domain.AccountAvatar;
import com.mossle.user.persistence.domain.AccountInfo;
import com.mossle.user.persistence.manager.AccountAvatarManager;
import com.mossle.user.persistence.manager.AccountInfoManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserAvatarService {
    private static Logger logger = LoggerFactory
            .getLogger(UserAvatarService.class);
    private AccountInfoManager accountInfoManager;
    private AccountAvatarManager accountAvatarManager;
    private StoreConnector storeConnector;

    public DataSource viewAvatar(Long accountId, int width, String tenantId)
            throws Exception {
        AccountInfo accountInfo = accountInfoManager.get(accountId);

        String key = null;

        if (accountInfo != null) {
            String hql = "from AccountAvatar where accountInfo=? and type='default'";
            AccountAvatar accountAvatar = accountAvatarManager.findUnique(hql,
                    accountInfo);

            if (accountAvatar != null) {
                key = accountAvatar.getCode();
            }
        }

        if (key == null) {
            key = "default.jpg";
        }

        StoreDTO storeDto = null;

        storeDto = storeConnector.getStore("avatar", key, tenantId);

        if (storeDto == null) {
            storeDto = storeConnector.getStore("avatar", "default.jpg",
                    tenantId);

            return storeDto.getDataSource();
        }

        if (width == 0) {
            return storeDto.getDataSource();
        }

        StoreDTO originalStoreDto = storeDto;
        int index = key.lastIndexOf(".");
        String prefix = key.substring(0, index);
        String suffix = key.substring(index);
        String resizeKey = prefix + "-" + width + suffix;

        StoreDTO resizeStoreDto = storeConnector.getStore("avatar", resizeKey,
                tenantId);

        if (resizeStoreDto == null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageUtils.zoomImage(originalStoreDto.getDataSource()
                    .getInputStream(), baos, width, width);
            logger.info("resizeKey : {}", resizeKey);
            resizeStoreDto = storeConnector.saveStore("avatar", resizeKey,
                    new ByteArrayDataSource(storeDto.getDataSource().getName(),
                            baos.toByteArray()), tenantId);
        }

        return resizeStoreDto.getDataSource();
    }

    @Resource
    public void setAccountInfoManager(AccountInfoManager accountInfoManager) {
        this.accountInfoManager = accountInfoManager;
    }

    @Resource
    public void setAccountAvatarManager(
            AccountAvatarManager accountAvatarManager) {
        this.accountAvatarManager = accountAvatarManager;
    }

    @Resource
    public void setStoreConnector(StoreConnector storeConnector) {
        this.storeConnector = storeConnector;
    }
}
