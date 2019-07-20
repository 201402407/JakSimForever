package apps.user.jaksimforever.data

import java.util.*

data class JoinData(val member_id: String, val member_pwd: String, val member_nickname: String)

data class JoinCardData(val member_id: String, val member_pwd: String, val member_nickname: String, var bank_name: String?,
               var card_num: String?, var card_cvc: Int?, var card_duedate: Date?, var card_pwd: Int?)