package com.example.core

interface Mediator

interface MediatorOwner<T : Mediator> {
    var mediator: T
}