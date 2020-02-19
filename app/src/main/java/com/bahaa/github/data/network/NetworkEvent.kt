package com.bahaa.github.data.network

import android.os.Handler
import android.os.Looper

import java.util.HashMap

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

class NetworkEvent private constructor() {

    /*
     * Step 2: Create a PublishSubject instance
     * which we use to publish events to all
     * registered subscribers in the app.
     */
    private var subject: PublishSubject<NetworkState>? = null

    /*
     * Step 3: Create a CompositeDisposable map.
     * We use CompositeDisposable to maintain the list
     * of subscriptions in a pool. And also to so that
     * we can dispose them all at once.
     */
    private val compositeDisposableMap = HashMap<Any, CompositeDisposable>()

    init {
        if (sSoleInstance != null) {
            throw RuntimeException("Use getInstance() method to get the single instance of this class.")
        }
    }


    /*
     * Step 2: Create a method to fetch the Subject
     * or create it if it's not already in memory.
     */
    private fun getSubject(): PublishSubject<NetworkState> {
        if (subject == null) {
            subject = PublishSubject.create()
            subject!!.subscribeOn(AndroidSchedulers.mainThread())
        }
        return subject as PublishSubject<NetworkState>
    }


    /*
     * Step 3: Create a method to fetch the CompositeDisposable Map
     * or create it if it's not already in memory.
     * We pass a key (in this case our key is the Activity or fragment
     * instance).
     */
    private fun getCompositeSubscription(`object`: Any): CompositeDisposable {
        var compositeSubscription = compositeDisposableMap[`object`]
        if (compositeSubscription == null) {
            compositeSubscription = CompositeDisposable()
            compositeDisposableMap[`object`] = compositeSubscription
        }
        return compositeSubscription
    }


    /*
     * Step 4: Use this method to Publish the NetworkState
     * to all the specified subscribers of the subject.
     */
    fun publish(networkState: NetworkState) {
        Handler(Looper.getMainLooper())
                .post { getSubject().onNext(networkState) }
    }


    /*
     * Step 4: Use this method to subscribe to the specified subject
     * and listen for updates on that subject.
     * Pass in an object (in this case the activity or fragment instance)
     * to associate the registration, so that we can unsubscribe later.
     */
    fun register(lifecycle: Any, action: Consumer<NetworkState>) {
        val disposable = getSubject().subscribe(action)
        getCompositeSubscription(lifecycle).add(disposable)
    }


    /*
     * Step 4: Use this method to Unregister this particular object
     * from the bus, removing all subscriptions.
     * This should be called in order to avoid memory leaks
     */
    fun unregister(lifecycle: Any) {
        //We have to remove the composition from the map, because once you unsubscribe it can't be used anymore
        val compositeSubscription = compositeDisposableMap.remove(lifecycle)
        compositeSubscription?.dispose()
    }

    companion object {

        @Volatile
        private var sSoleInstance: NetworkEvent? = null

        val instance: NetworkEvent?
            get() {
                if (sSoleInstance == null) {
                    synchronized(NetworkEvent::class.java) {
                        if (sSoleInstance == null) sSoleInstance = NetworkEvent()
                    }
                }
                return sSoleInstance
            }
    }
}
