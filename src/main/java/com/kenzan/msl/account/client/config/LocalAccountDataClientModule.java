package com.kenzan.msl.account.client.config;

import com.google.inject.AbstractModule;
import com.kenzan.msl.account.client.services.AccountDataClientService;
import com.kenzan.msl.account.client.services.AccountDataClientServiceStub;

/**
 * @author Kenzan
 */
public class LocalAccountDataClientModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(AccountDataClientService.class).to(AccountDataClientServiceStub.class).asEagerSingleton();
  }
}
