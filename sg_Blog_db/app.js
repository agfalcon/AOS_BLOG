import express from "express"
import mysql from "mysql"

const app = express()


const dbBlog = mysql.createConnection({ 
	host: 'localhost',
	user: 'root',
		password: 'smile1234',
		database: 'blogdb'
})

dbBlog.connect()


//app.use('/image', express.static('images'));

app.get('/', (req, res) => {
  res.send('Success!')
})

app.get('/essay', (req, res)=>{
	const query = 'select * from edit'
	dbBlog.query(query, (err, rows)=>{
		if(err) return console.log(err)
		res.send(rows)
	})
})

app.get('/comment/:edit_id', (req, res)=>{
	const id = req.params.edit_id
	const query = 'select * from comment where edit_id = ?'
	dbBlog.query(query, id, (err, rows)=>{
		if(err) return console.log(err)
		res.send(rows)
	})
})

app.get('/delete/:id', (req,res)=>{
	const id = Number(req.params.id)
	console.log(id)
	const query = 'DELETE FROM edit WHERE id = ?'
	dbBlog.query(query, id, (err, rows)=>{
		if(err) return console.log(err)
		res.send(rows)
	})
})

//app.use(express.json())
 app.post('/edit', (req, res)=>{
	 var editData
	 req.on('data', (data) =>{
		 editData = JSON.parse(data)
		 const query = 'INSERT INTO edit (title, date, content, comment_num) VALUES (?, ?, ?, ?);'
		dbBlog.query(query, [editData.title, editData.date, editData.content, editData.comment_num], (err, rows)=>{
			if(err) return console.log(err)
			res.send(rows)
		})
	 })
    
 })

app.post('/editComment', (req, res)=>{
	 var commentData
	 req.on('data', (data) =>{
		 commentData = JSON.parse(data)
		
		 const query2 = 'INSERT INTO comment (Edit_ID, date, name, content) VALUES (?, ?, ?, ?);'
		dbBlog.query(query2, [commentData.editId, commentData.date, commentData.name, commentData.content], (err, rows)=>{
			if(err) return console.log(err)
			res.send(rows)
			var commentNum
			const query1 = 'select `comment_num` from edit where id = ?'
			dbBlog.query(query1, commentData.editId, (err, rows)=>{
				if(err) return console.log(err)
				commentNum = Number(rows[0].comment_num)
				console.log(commentNum)
				commentNum = commentNum + 1
				console.log(commentNum)
				const query3 = 'UPDATE edit SET `comment_num` = ? WHERE id = ?;'
				dbBlog.query(query3, [commentNum, commentData.editId], (err, rows)=>{
					if(err) return console.log(err)
				})
			})
		})
		
	 })
    
 })


app.listen('3000', ()=>{
	console.log("Server Started")
})

