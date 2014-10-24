package com.example.exceptions

case class ProfileNotFoundException(profileId: Int) extends RuntimeException(s"The requested Profile with id [$profileId] cannot be found")