package demo.state

import demo.account.Account
import demo.event.AccountEvent
import demo.function.LambdaFunctions

class AccountOperations(private val lamdaFunctions: LambdaFunctions, override val account: Account) : AccountStateMachineContext {

    override fun createAccount(event: AccountEvent): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmAccount(event: AccountEvent): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateAccount(event: AccountEvent): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun archiveAccount(event: AccountEvent): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun suspendAccount(event: AccountEvent): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unarchiveAccount(event: AccountEvent): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsuspendAccount(event: AccountEvent): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
