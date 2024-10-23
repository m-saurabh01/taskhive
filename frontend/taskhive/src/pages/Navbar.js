import React from 'react';
import './nav.css'



const Navbar =function(){
   return (
       <nav id='topnav'>
           <a id='logo' className='nav-link' href='#'>
               TaskHive
           </a>
           <div className='nav-right'>
               <a href='#footer' id='about' className='nav-link'>
                   Services
               </a>
               <a href='#footer' id='contact' className='nav-link'>
                   Contact Us
               </a>
           </div>
       </nav>
   );

}

export default Navbar;