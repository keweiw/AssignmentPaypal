/*
 * GET HOME PAGE
 */

exports.index = function(req, res){
    // see if we already have a session
    res.render('index', {
        title: 'Paypal Assignment',
        session: req.session,
    });
};
