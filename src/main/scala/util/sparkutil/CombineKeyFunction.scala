package util.sparkutil

import bean.AiUserActionInfo

/**
  * Created by lenovo on 2018/7/11.
  */

//spark combinekey所需传递的函数
object CombineKeyFunction{
  def createBine[T](user:T):List[T]={
    List(user)
  }
  def mergeValue[T](list1:List[T],user:T):List[T] ={
    user::list1
  }

  def mergeCombine[T](list1:List[T],list2: List[T]) ={
    list1:::list2
  }
}
