<!DOCTYPE html>
<html>
<head><title></title>
    <link rel="stylesheet" href="tachyons.min.css">
</head>
<body class="ph3 pt0 pb4 mw7 center sans-serif">
<h1 class="f2 mb0"><span class="gold">SOM</span>repo</h1>
<p class="f5 mt1 mb4 lh-copy">Persistence driver</p>
<form action="/order" method="POST" enctype="multipart/form-data">
    <ol class="list pl0">
        <div>
        <li class="mv3"><label class="f6 b db mb2" for="key">Key</label>
            <input class="f4 db border-box hover-black w-100 measure ba b--black-20 pa2 br2 mb2" rows="5"
                      name="key"><#if key??>${key}</#if></input>
        </li></div>
        <div>
        <li class="mv3"><label class="f6 b db mb2" for="order">Order</label>
            <textarea class="f4 db border-box hover-black w-100 measure ba b--black-20 pa2 br2 mb2" rows="5"
                      name="order"><#if order??>${order}</#if></textarea>
        <input class="f6 link dim br1 ba bw1 ph3 pv2 mb2 dib black bg-white pointer" type="submit" value="Save"
               name="save">
        </li></div>
    </ol>
</form>
<ul class="list pl0"><p class="f6 b db mb2">Orders</p>
    <#if orders??>
    <#list orders as order>
    <li class="mv3 bb bw2 b--light-yellow bg-washed-yellow ph4 pv2"><p class="measure"></p>
        <p>${order}</p>
        <p></p>
    </li>
    <#else>
    <p class="lh-copy f6">You don't have any orders yet.</p>
</#list>
</#if>
</ul>
        </body>
        </html>