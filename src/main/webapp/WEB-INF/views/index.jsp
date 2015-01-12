<%@ page language="java" contentType="text/html; UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Joke Bot Debug Form</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

  <script type="text/javascript">
    $(function() {
      $('button[name="go"]').on('click', function() {
        var btn = $(this).button('loading');
        
        var form = $(this).closest('form');
        var answer = $('#answer_' + form.find('[name="bot_id"]').val());
        answer.text('');
  
        $.ajax({
          url : "/jokebot-service/reaction",
          type : 'POST',
          data : form.serializeArray()
        }).done(function(result) {
          answer.text(result.name + ':' + result.answer);
        }).fail(function() {
          alert('error');
        }).always(function() {
          btn.button('reset');
        });
        return false;
      });
  
      $('button[name="store"]').on('click', function() {        
        var form = $(this).closest('form');
        $.ajax({
          url : "/jokebot-service/store_apikey",
          type : 'POST',
          data : form.serializeArray()
        }).done(function(result) {
          $('#apikey').val('');
        }).fail(function() {
          alert('error');
        }).always(function() {
        });
        return false;
      });
    });
  </script>
</head>
<body>
<div class="container">
<div class="jumbotron">
  <h1>Joke Bot Debug Form</h1>
  <form>
    <div class="form-group">
      <div class="col-sm-10">
        <input id="apikey" type="text" name="apikey" class="form-control" placeholder="docomo apikey を送信してください">
      </div>
    </div>
    <button type="submit" class="btn btn-primary" name="store">送信</button>
  </form>
</div>

<div class="panel panel-default">
  <div class="panel-body">
  <form class="form-inline">
    <div class="form-group">
      <p class="form-control-static">あけみちゃんに</p>
    </div>
    <div class="form-group">
      <input type="hidden" name="bot_id" value="1">
      <input type="text" name="keyword" value="スイカ食べたいな" class="form-control input-sm">
    </div>
    <div class="form-group">
      <p class="form-control-static">と</p>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" name="go" data-loading-text="Loading...">話しかける</button>
    <span id="answer_1"></span>
  </form>
  <form class="form-inline">
    <div class="form-group">
      <p class="form-control-static">細貝さんに</p>
    </div>
    <div class="form-group">
      <input type="hidden" name="bot_id" value="2">
      <input type="text" name="keyword" value="スイカ食べたいな" class="form-control input-sm">
    </div>
    <div class="form-group">
      <p class="form-control-static">と</p>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" name="go">話しかける</button>
    <span id="answer_2"></span>
  </form>
   </div>
</div>

</div>
</body>
</html>