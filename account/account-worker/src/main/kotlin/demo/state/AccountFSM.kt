package demo.state

import demo.account.Account
import demo.account.AccountStatus
import demo.account.AccountStatus.ACCOUNT_ACTIVE
import demo.account.AccountStatus.ACCOUNT_ARCHIVED
import demo.account.AccountStatus.ACCOUNT_CONFIRMED
import demo.account.AccountStatus.ACCOUNT_PENDING
import demo.account.AccountStatus.ACCOUNT_SUSPENDED
import demo.event.AccountEvent
import demo.event.AccountEventType
import io.jumpco.open.kfsm.stateMachine

interface AccountStateMachineContext {
    fun createAccount(event: AccountEvent): Account
    fun confirmAccount(event: AccountEvent): Account
    fun activateAccount(event: AccountEvent): Account
    fun archiveAccount(event: AccountEvent): Account
    fun suspendAccount(event: AccountEvent): Account
    fun unarchiveAccount(event: AccountEvent): Account
    fun unsuspendAccount(event: AccountEvent): Account
}

class AccountFSM(context: AccountStateMachineContext, initialState: AccountStatus) {
    companion object {
        private val definition =
            stateMachine(
                AccountStatus.values().toSet(),
                AccountEventType.values().toSet(),
                AccountStateMachineContext::class,
                demo.event.AccountEvent::class,
                demo.account.Account::class
            ) {

                whenState(AccountStatus.ACCOUNT_CREATED) {
                    onEvent(AccountEventType.ACCOUNT_CREATED to ACCOUNT_PENDING) { event ->
                        require(event != null) { "event is required " }
                        createAccount(event)
                    }
                }
                whenState(ACCOUNT_PENDING) {
                    onEvent(AccountEventType.ACCOUNT_CONFIRMED to ACCOUNT_CONFIRMED) { event ->
                        require(event != null) { "event is required " }
                        confirmAccount(event)
                    }
                }
                whenState(ACCOUNT_CONFIRMED) {
                    onEvent(AccountEventType.ACCOUNT_ACTIVATED to ACCOUNT_ACTIVE) { event ->
                        require(event != null) { "event is required " }
                        activateAccount(event)
                    }
                }
                whenState(ACCOUNT_ACTIVE) {
                    onEvent(AccountEventType.ACCOUNT_ARCHIVED to ACCOUNT_ARCHIVED) { event ->
                        require(event != null) { "event is required " }
                        archiveAccount(event)
                    }
                    onEvent(AccountEventType.ACCOUNT_SUSPENDED to ACCOUNT_SUSPENDED) { event ->
                        require(event != null) { "event is required " }
                        suspendAccount(event)
                    }
                }
                whenState(ACCOUNT_ARCHIVED) {
                    onEvent(AccountEventType.ACCOUNT_ACTIVATED to ACCOUNT_ACTIVE) { event ->
                        require(event != null) { "event is required " }
                        unarchiveAccount(event)
                    }
                }
                whenState(ACCOUNT_SUSPENDED) {
                    onEvent(AccountEventType.ACCOUNT_ACTIVATED to ACCOUNT_ACTIVE) { event ->
                        require(event != null) { "event is required " }
                        unsuspendAccount(event)
                    }
                }
            }.build()
    }

    private val fsm = definition.create(context, initialState)
    fun sendEvent(event: AccountEvent): Account? = fsm.sendEvent(event.type, event)
}
