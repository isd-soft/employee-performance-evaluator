const path = require('path');
const express = require('express');
const app = express();

app.use(express.static('./dist/epe'));

app.get('/*', function(req, res) {
  res.sendFile('index.html', {root: 'dist/epe/'}
);
});

app.listen(process.env.PORT || 4200, () => {
  console.log('Connected to Port'); //Listening on port 4200
});
