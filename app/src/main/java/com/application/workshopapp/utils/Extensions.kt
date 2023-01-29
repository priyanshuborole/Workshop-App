package com.application.workshopapp.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.application.workshopapp.R
import com.application.workshopapp.data.model.Workshop


fun Context.progressDialog(): Dialog {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.layout_custom_progress_dialog)
    return dialog
}

fun ArrayList<Workshop>.addDummyData() {
    add(Workshop(1,"Android Development", "https://images.unsplash.com/photo-1607252650355-f7fd0460ccdb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80","This is a workshop on Android development",  "01/01/2022"))
    add(Workshop(2,"Ios Development", "https://images.unsplash.com/photo-1531986362435-16b427eb9c26?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80","This is a workshop on iOS development", "02/01/2022"))
    add(Workshop(3,"Web Development", "https://images.unsplash.com/photo-1593720213428-28a5b9e94613?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80","This is a workshop on Web development",  "03/01/2022"))
    add(Workshop(4,"Machine Learning", "https://images.unsplash.com/photo-1591453089816-0fbb971b454c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80","This is a workshop on Machine Learning",  "04/01/2022"))
    add(Workshop(5,"Cloud Computing", "https://images.unsplash.com/photo-1517483000871-1dbf64a6e1c6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=869&q=80","This is a workshop on Cloud Computing",  "05/01/2022"))
    add(Workshop(6,"DevOps", "https://images.unsplash.com/photo-1667372335937-d03be6fb0a9c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZGV2b3BzfGVufDB8MHwwfHw%3D&auto=format&fit=crop&w=600&q=60","This is a workshop on DevOps",  "06/01/2022"))
    add(Workshop(7,"Firebase", "https://images.unsplash.com/photo-1667372393086-9d4001d51cf1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8YmFja2VuZCUyMGRldmVsb3BlcnxlbnwwfDB8MHx8&auto=format&fit=crop&w=600&q=60","This is a workshop on Firebase",  "07/01/2022"))
    add(Workshop(8,"Flutter", "https://images.unsplash.com/photo-1617040619263-41c5a9ca7521?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Zmx1dHRlcnxlbnwwfDB8MHx8&auto=format&fit=crop&w=600&q=60","This is a workshop on Flutter",  "08/01/2022"))
}