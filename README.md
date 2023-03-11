# Voici notre super application mobile (Android) !

>- c'est un controller pour un jeu qui peut être trouvé à la racine du projet (AndroidGameServer.jar). Pour jouer il suffit de lancer le jar et de lancer l'application sur un téléphone android et d e saisir l'adresse ip affiché par la console du serveur.
>- <img width="780" alt="image" src="https://user-images.githubusercontent.com/118441536/224474496-f2af0011-d3c2-49cb-aa0d-0ee8eadd3f25.png">

## 1. Nous avons mis en place une **frame d'acceuil**, elle comporte:
+ une **liste déroulante** contenant l'ip, le nom et la couleur utilisés. C'est un **historique**. Cela permet de récupérer ses presets et de ne pas retaper l'ip et son nom. Toutes ses informations sont stockées dans un File spécifique à l'application.
+ Une **bannière** dans laquelle il y'a un **bouton d'aide** qui ouvre une frame pop-up (pas de nouvelle activité pour ceci). Il y'a un bouton **settings** aussi pour accéder aux paramètres de l'application, on peut changer sur cette activité la couleur de son pion.
+ 2 inputText, un pour l'ip du serveur et un pour le nom (**rempli automatiquement** quand on choisi un preset dans l'historique)
+ 1 boutons de type toggle pour **choisir** soit le premier **controlleur** soit le 2ème.
+ un bouton pour se **connecter** au serveur
    

## 2. Nous avons donc mis en place 2 **controlleurs** :
+ 1 avec un **joystick** et des **fonctionnalités de _triches_**
+ 1 avec des **boutons** (en forme de croix) sans les **fonctionnalités de _triches_**
>- Ces deux controlleurs sont lancé uniquement si la connection au serveur de jeu à reussi
>- De plus un thread s'occupe de dire au serveur que le jour est toujours connecté tant que la frame de jeu n'est pas quitté
>- Attention : lorsque l'on utilise le joystck lors du retour on quitte l'application mais ce n'est pas le cas si l'application est installé à partir d'un fichier apk sur une machine qui n'est pas en mode dévelopement
>- La déconnection s'effectue lorsque le controlleur est quitté


## 3. Concernant le **controlleur avec boutons** (sans triche):
+ Mise en place des boutons Avancer, tourner à droite, tourner à gauche, le bouton reculer ne fonctionne pas car il est inutile pour moi
+ Mise en place du bouton tirer, il ne fait que tirer devant le joueur.
+ Essai de mise en place de **changement de direction en fonction de l'orientation** du pion (cf. [_orientation.jpg_](https://gitlab.iut-valence.fr/duratm/duratfarretandroid/-/blob/master/orientation.jpg)). ![This is an image](https://gitlab.iut-valence.fr/duratm/duratfarretandroid/-/raw/master/orientation.jpg)


1. Pour le changement de direction en fonction de l'orientation plusieurs options s'offraient à moi :
- J'ai d'abord commencé par changer la **place** des boutons en fonction de l'orientation du joueur. Mais cette solution demande trop de ressource et n'est pas top niveau IHM
- J'en ai donc conclus qu'il fallait que je change le **contenu des sendMessage()** en fonction des orientations.
- Mais un autre problème est survenu ! Comment récupérer les orientations ? 
> J'ai mis en place un **pattern producteur** (producteur de'orientation, thread qui tourne en boucle qui récupère les orientations du pion) **consommateur** (threads du controlleur qui récupère les dernières orientations enregistrées) (cf. [_voir ce commit_](https://gitlab.iut-valence.fr/duratm/duratfarretandroid/-/commit/9ae16f59ab88248182c0c7e17f887097c6414cbe)).

## 4. Concernant le **controlleur avec Joysticke** (et triche):
+ Mise en place du **joystick** fourni avec un léger correctif pour qu'il soit plus facilement utilisable (augmentation de 0,5 de la valeur des ordonnées afin que le robot s'arrête quand il relache le joystick et que son interaction ressemble un peut plus à celle d'un joystick son intéraction est "centré")
+ Mise en place de trois boutons toggle avec les fonctionnalité suivantes : **se cacher**, **tirer automatiquement** et **viser automatiquement les ennemis les plus proches**.
+ Un **bouton de tire**
+ Des Boutons afin de faire des **émotes**
>- La fonctionnalité visé automatique est géré grace à un thread pour le quel on a crée un runnable et qui se charge d'ajuster la position de tir quand il y en a besoin.    

## 5. Sur l'application en général:
+ Mise en place de l'internationalisation de tout les textes les paramètres par défaut sont en anglais mais l'application est disponible en français aussi
+ Modification des thèmes dark et par défaut (pour une couleur orange)
+ Mise en place d'une image d'application personnalisé 
