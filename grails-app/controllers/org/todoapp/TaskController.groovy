package org.todoapp

import grails.converters.JSON
class TaskController {
	def show={
		if(params.id && Task.exists(params.id)){
			render Task.findById(params.id) as JSON
		}else{
			render Task.list() as JSON
		}
	}

	def save={
		println "inside [save] rest method"
		def jsonTaskObj=request.JSON['todo'];
		def task=new Task(jsonTaskObj);
		if(task.save(flush: true)){
			render task as JSON
		}else{
		}
	}

	def delete={
		println "inside [delete] rest method"
		def todo
		if(params.id){
			todo=Task.get(params.id)
			if(todo){
				todo.delete(flush: true)
			}
		}
		render todo as JSON
	}

	def option={
		println "inside [option] rest method"
		render status:200
	}

	def update={
		println "inside [update] rest method"
		def todo
		def jsonTaskObj=request.JSON['todo'];
		if(params.id){
			todo=Task.get(params.id)
			if(todo){
				todo.title=jsonTaskObj.title
				todo.description=jsonTaskObj.description
				todo.isCompleted=jsonTaskObj.isCompleted
				println todo.isCompleted
				todo.dueDate=new Date();
				if(todo.save(flush: true))
				render todo as JSON
			}
		}
	}

	def beforeInterceptor = {
		response.setHeader('Access-Control-Allow-Origin', '*')
        response.setHeader('Access-Control-Allow-Methods', 'POST, PUT, GET, OPTIONS, PATCH , DELETE')
        response.setHeader('Access-Control-Allow-Headers', 'Origin, X-Additional-Headers-Example,Content-type,Accept')
        response.setHeader('Access-Control-Allow-Credentials', 'true')
        response.setHeader('Access-Control-Max-Age', '1728000')
	}
}
