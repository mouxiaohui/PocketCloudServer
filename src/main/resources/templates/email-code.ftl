<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            width: 450px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }
        .header {
            background-color: #71ac49;
            color: white;
            padding: 10px;
            text-align: center;
            border-radius: 8px;
            font-size: 18px;
        }
        .content {
            margin-top: 20px;
        }
        .content p {
            margin: 10px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">Pocket Cloud</div>
    <div class="content">
        <h2>邮箱验证码</h2>
        <p>尊敬的用户<span style="color: #3b5998">${to}</span>，您好！</p>
        <p>
            您的验证码是：<span style="font-size: 18px; font-weight: bold">${code}</span>，请在
            <span style="font-weight: bold">5</span> 分钟内进行验证。如果该验证码不为您本人申请，请无视。
        </p>
    </div>
</div>
</body>
</html>
