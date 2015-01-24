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
        var bod_id = form.find('[name="bot_id"]').val();
        var answer = $('#answer_' + bod_id);
        answer.text('');
  
        $.ajax({
          url : "bot/reaction",
          type : 'POST',
          data : form.serializeArray()
        }).done(function(result) {
          answer.text(result.bot_name + ':' + result.answer);
          $('#picture_' + bod_id).prop('src', result.picture_url);
        }).fail(function() {
          alert('error');
        }).always(function() {
          btn.button('reset');
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
</div>

<div class="panel panel-default">
  <div class="panel-body">
    <form class="form-inline">
      <div class="form-group">
        <img id="picture_1" class="form-control-static"/>
        <p class="form-control-static">朱美ちゃんに</p>
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
        <img id="picture_2" class="form-control-static"/>
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
    
    <form class="form-inline">
      <div class="form-group">
        <img id="picture_3" class="form-control-static"/>
        <p class="form-control-static">しりとりっ娘に</p>
      </div>
      <div class="form-group">
        <input type="hidden" name="bot_id" value="3">
        <input type="text" name="keyword" value="スイカ食べたいな" class="form-control input-sm">
      </div>
      <div class="form-group">
        <p class="form-control-static">と</p>
      </div>
      <button type="submit" class="btn btn-primary btn-sm" name="go">話しかける</button>
      <span id="answer_3"></span>
    </form>
    
    <form class="form-inline">
      <div class="form-group">
        <img id="picture_4" class="form-control-static"/>
        <p class="form-control-static">A.L.I.C.E</p>
      </div>
      <div class="form-group">
        <input type="hidden" name="bot_id" value="4">
        <input type="text" name="keyword" value="スイカ食べたいな" class="form-control input-sm">
      </div>
      <div class="form-group">
        <p class="form-control-static">と</p>
      </div>
      <button type="submit" class="btn btn-primary btn-sm" name="go">話しかける</button>
      <span id="answer_4"></span>
    </form>
  </div>
</div>

</div>
</body>
</html>